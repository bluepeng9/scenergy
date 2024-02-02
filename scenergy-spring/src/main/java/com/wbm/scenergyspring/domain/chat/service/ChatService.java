package com.wbm.scenergyspring.domain.chat.service;

import com.wbm.scenergyspring.domain.chat.dto.ChatMessageDto;
import com.wbm.scenergyspring.domain.chat.dto.ChatRoomDto;
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
     * 메시지 전송 서비스 method
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
        //메시지 타입 ENTER, EXIT, TALK 분기
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
        }
        //메시지 entity 생성
        ChatMessage chatMessage = ChatMessage.createChatMessage(
                command.getUserId(),
                command.getMessageText(),
                chatRoom
        );
        //메시지 dto 생성
        ChatMessageDto chatMessageDto = ChatMessageDto.builder()
                .chatRoomId(command.getRoomId())
                .messageText(chatMessage.getMessageText())
                .senderId(command.getUserId())
                .createdAt(chatMessage.getCreatedAt())
                .flag(1)
                .build();
        //메시지 전송
        // TODO: roomId로 방 주소를 구분하면 주소만 가지고 다른방에 채팅을 할 수 있음. 따라서 암호화 필요 (UUID?)
        redisPublisher.publish(redisChatRepository.getTopic(command.getRoomId()), chatMessageDto);
        //RDB 저장
        chatRoom.addChatMessages(chatMessage);
        //redis 저장
        redisChatRepository.chatMessageSave(chatMessageDto);
        return chatMessageRepository.save(chatMessage).getId();
    }

    /**
     * 채팅방 생성 서비스 method
     *
     * @param command roomName, status, users
     * @return roomId
     */
    @Transactional(readOnly = false)
    public Long createChatRoom(CreateChatRoomCommand command) {
        if (command.getStatus() == 0) {
            //TODO: 같은 스테이터스를 가지고 챗 맴버도 동일한 방을 찾아봐야함 존재한다면 그 룸 id return
        }
        ChatRoom newChatRoom = ChatRoom.createNewRoom(
                command.getRoomName(),
                command.getStatus()
        );
        // 맴버 초대
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

    /**
     * 채팅방 이름 바꾸기 서비스 method
     * @param command roomName, roomId
     * @return roomId
     */
    @Transactional(readOnly = false)
    public Long renameChatRoom(RenameChatRoomCommand command) {
        //room 조회
        ChatRoom chatRoom = chatRoomRepository.findById(command.getRoomId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 채팅룸"));
        //RDB저장
        chatRoom.rename(command.getRoomName());
        //redis
        redisChatRepository.renameChatRoom(chatRoom.getId(), command.getRoomName());
        return chatRoom.getId();
    }

    /**
     * 맴버 초대 서비스 method
     * @param command roomId, users
     * @return roomId
     */
    @Transactional(readOnly = false)
    public Long inviteChatRoom(InviteChatRoomCommand command) {
        ChatRoom chatRoom = chatRoomRepository.findById(command.getRoomId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 채팅룸"));
        //RDB 저장
        chatRoom.inviteChatUsers(command.getUsers());
        //redis 저장
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

    /**
     * 내 채팅방 조회 서비스 method
     * @param command userId
     * @return List<ChatRoom>
     */
    public List<ChatRoomDto> listMyChatRoom(ListMyChatRoomCommand command) {
        List<ChatRoomDto> chatRoomDtoList = new ArrayList<>();
        List<ChatRoom> myChatRoom = chatRoomRepository.findMyChatRoom(command.getUserId());
        for (ChatRoom chatRoom : myChatRoom) {
            ChatRoomDto chatRoomDto = ChatRoomDto.from(chatRoom);
            List<User> chatUsers = chatUserRepository.findAllByChatRoom(chatRoom);
            ChatMessage firstChatMessage = chatMessageRepository.findTop1ByChatRoomOrderByCreatedAtDesc(chatRoom).orElseThrow(() -> new EntityNotFoundException("채팅방에 message가 존재하지 않음"));
            ChatMessageDto firstChatMessageDto = ChatMessageDto.from(firstChatMessage);
            chatRoomDto.setFirstChatMessage(firstChatMessageDto);
            chatRoomDto.setChatUsers(chatUsers);
            chatRoomDtoList.add(chatRoomDto);
        }
        return chatRoomDtoList;
    }

    /**
     * 채팅방 내 채팅 조회 method
     * @param command roomId
     * @return List<ChatMessage>
     */
    public List<ChatMessageDto> loadChatMessage(LoadChatMessageCommand command) {
        List<ChatMessageDto> messageList = new ArrayList<>(); // return
        //메시지 조회 후 dto에 삽입
        ChatMessageDto lastChatMessageDto = ChatMessageDto.from(chatMessageRepository.findById(command.getChatMessageId()).get());
        //redis 조회
        List<ChatMessageDto> redisMessageList = redisChatRepository.loadChatMessage(lastChatMessageDto);
        if (redisMessageList == null) { // redis에 존재하지 않는 경우
            ChatRoom chatRoom = chatRoomRepository.findById(lastChatMessageDto.getChatRoomId())
                    .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 채팅룸"));
            //RDB 조회
            List<ChatMessage> RDBMessageList = chatMessageRepository.findTop100OrderByCreatedAtDescWhereCurrent(lastChatMessageDto);
            for (ChatMessage chatMessage : RDBMessageList) {
                //메시지 dto 생성
                ChatMessageDto chatMessageDto = ChatMessageDto.from(chatMessage);
                //redis 등록
                redisChatRepository.chatMessageSave(chatMessageDto);
                messageList.add(chatMessageDto);
            }
        } else {
            messageList.addAll(redisMessageList);
        }
        return messageList;
    }

    public ChatRoom findChatRoom(Long roomId) {
        return chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 채팅룸"));
    }
}