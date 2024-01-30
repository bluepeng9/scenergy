package com.wbm.scenergyspring.domain.chat.service;

import com.wbm.scenergyspring.domain.chat.entity.ChatMessage;
import com.wbm.scenergyspring.domain.chat.entity.ChatRoom;
import com.wbm.scenergyspring.domain.chat.entity.ChatUser;
import com.wbm.scenergyspring.domain.chat.redis.RedisPublisher;
import com.wbm.scenergyspring.domain.chat.repository.ChatMessageRepository;
import com.wbm.scenergyspring.domain.chat.repository.ChatRoomRepository;
import com.wbm.scenergyspring.domain.chat.repository.ChatUserRepository;
import com.wbm.scenergyspring.domain.chat.repository.RedisChatRepository;
import com.wbm.scenergyspring.domain.chat.service.command.*;
import com.wbm.scenergyspring.domain.user.entity.User;
import com.wbm.scenergyspring.domain.user.repository.UserRepository;
import com.wbm.scenergyspring.global.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatService {

    final ChatRoomRepository chatRoomRepository;
    final ChatMessageRepository chatMessageRepository;
    final ChatUserRepository chatUserRepository;
    final UserRepository userRepository;
    final RedisChatRepository redisChatRepository;
    final RedisPublisher redisPublisher;

    /**
     * 메시지 전송
     *
     * @param command: userId, roomId, messageText, messageType
     * @return MessageId (0L: 채팅방 삭제)
     */
    @Transactional(readOnly = false)
    public Long sendMessage(CreatePubMessageCommand command) {
        //chatUser 조회||생성
        ChatRoom chatRoom = chatRoomRepository.findById(command.getRoomId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 채팅룸"));
        User user = userRepository.findById(command.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저"));
        ChatUser chatUser = chatUserRepository.findByChatRoomAndUser(chatRoom, user)
                .orElse(ChatUser.createChatUser(chatRoom, user));

        String messageType = command.getMessageType();
        if (messageType.equals("EXIT")) {
            command.setMessageText(user.getNickname() + "님이 퇴장하셨습니다.");
            int remainingMembersCount = chatUser.leaveRoom();
            int redisRemainingMembersCount = redisChatRepository.updateMemberCount(command.getRoomId(), -1);
            if (redisRemainingMembersCount <= 0 || remainingMembersCount <= 0) { // 채팅방 삭제
                chatRoomRepository.delete(chatRoom);
                return 0L;
            }
        } else if (messageType.equals("ENTER")) {
            command.setMessageText(user.getNickname() + "님이 초대되셨습니다.");
            chatUser.joinRoom();
        }
        ChatMessage chatMessage = ChatMessage.createChatMessage(
                command.getUserId(),
                command.getMessageText(),
                chatRoom
        );

        //전송
        // TODO: roomId로 방 주소를 구분하면 주소만 가지고 다른방에 채팅을 할 수 있음. 따라서 암호화 필요 (UUID?)
        redisPublisher.publish(redisChatRepository.getTopic(command.getRoomId()), chatMessage);

        //RDB 저장
        chatRoom.addChatMessages(chatMessage);
        //redis 저장
        redisChatRepository.chatMessageSave(chatMessage);
        return chatMessageRepository.save(chatMessage).getId();
    }

    @Transactional(readOnly = false)
    public Long createChatRoom(CreateChatRoomCommand command) {
        if (command.getStatus() == 0) {
            //TODO: 같은 스테이터스를 가지고 챗 맴버도 동일한 방을 찾아봐야함
        }
        ChatRoom newChatRoom = ChatRoom.createNewRoom(
                command.getRoomName(),
                command.getStatus()
        );
        newChatRoom.inviteChatUsers(command.getUsers());
        //mysql 등록
        Long roomId = chatRoomRepository.save(newChatRoom).getId();
        //redis 등록
        redisChatRepository.createChatRoom(roomId, newChatRoom.getName(), newChatRoom.getStatus(), newChatRoom.getChatUsers().size());
        redisChatRepository.enterChatRoom(roomId);

        //초대됨 메시지 발행
        for (User user : command.getUsers()) {
            CreatePubMessageCommand pubMessageCommand = CreatePubMessageCommand.builder()
                    .messageType("ENTER")
                    .roomId(roomId)
                    .userId(user.getId())
                    .build();
            sendMessage(pubMessageCommand);
        }
        return roomId;
    }

    @Transactional(readOnly = false)
    public Long renameChatRoom(RenameChatRoomCommand command) {
        ChatRoom chatRoom = chatRoomRepository.findById(command.getRoomId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 채팅룸"));
        chatRoom.rename(command.getRoomName());
        redisChatRepository.renameChatRoom(chatRoom.getId(), command.getRoomName());
        return chatRoom.getId();
    }

    @Transactional(readOnly = false)
    public Long inviteChatRoom(InviteChatRoomCommand command) {
        ChatRoom chatRoom = chatRoomRepository.findById(command.getRoomId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 채팅룸"));
        chatRoom.inviteChatUsers(command.getUsers());
        redisChatRepository.updateMemberCount(command.getRoomId(), command.getUsers().size());

        //초대됨 메시지 발행
        for (User user : command.getUsers()) {
            CreatePubMessageCommand pubMessageCommand = CreatePubMessageCommand.builder()
                    .messageType("ENTER")
                    .roomId(command.getRoomId())
                    .userId(user.getId())
                    .build();
            sendMessage(pubMessageCommand);
        }
        return chatRoom.getId();
    }

    public List<ChatRoom> listMyChatRoom(ListMyChatRoomCommand command) {
        List<ChatRoom> myChatRoom = chatRoomRepository.findMyChatRoom(command.getUserId());
        return myChatRoom;
    }

    public List<ChatMessage> loadChatMessage(LoadChatMessageCommand command) {
        List<ChatMessage> messageList = new ArrayList<>();
        List<ChatMessage> redisMessageList = redisChatRepository.loadChatMessage(command.getRoomId());
        if (redisMessageList == null || redisMessageList.isEmpty()) {
            ChatRoom chatRoom = chatRoomRepository.findById(command.getRoomId())
                    .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 채팅룸"));
            List<ChatMessage> RDBMessageList = chatMessageRepository.findTop100ByChatRoomOrderByCreatedAtAsc(chatRoom);
            for (ChatMessage chatMessage : RDBMessageList) {
                redisChatRepository.chatMessageSave(chatMessage);
                messageList.add(chatMessage);
            }
        } else {
            messageList.addAll(redisMessageList);
        }
        return messageList;
    }


}