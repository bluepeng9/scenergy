package com.wbm.scenergyspring.domain.chat.service;

import com.wbm.scenergyspring.domain.chat.dto.ChatMessageDto;
import com.wbm.scenergyspring.domain.chat.dto.ListChatRoomDto;
import com.wbm.scenergyspring.domain.chat.dto.RedisChatRoomDto;
import com.wbm.scenergyspring.domain.chat.dto.UnreadMessageDto;
import com.wbm.scenergyspring.domain.chat.entity.ChatMessage;
import com.wbm.scenergyspring.domain.chat.entity.ChatRoom;
import com.wbm.scenergyspring.domain.chat.entity.ChatUser;
import com.wbm.scenergyspring.domain.chat.entity.UnreadMessage;
import com.wbm.scenergyspring.domain.chat.redis.RedisPublisher;
import com.wbm.scenergyspring.domain.chat.repository.*;
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
    final UnreadMessageRepository unreadMessageRepository;
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
        //시스템 알림
        if (messageType.equals("OPER")) {
            //operation 생성
            ChatMessageDto chatMessageDto = ChatMessageDto.builder()
                    .chatRoomId(command.getRoomId())
                    .senderId(0L)
                    .operationCode(1)
                    .build();
            redisPublisher.publish(redisChatRepository.getTopic(command.getRoomId()), chatMessageDto);
            return 0L;
        } else if (messageType.equals("EXIT")) {
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
        List<Long> offlineMembers = redisChatRepository.findOfflineMember(RedisChatRoomDto.from(chatRoom));
        int unreadCount = offlineMembers.size();

        //메시지 entity 생성
        ChatMessage chatMessage = ChatMessage.createChatMessage(
                command.getUserId(),
                command.getMessageText(),
                chatRoom,
                unreadCount
        );
        //RDB 저장
        chatMessage = chatMessageRepository.save(chatMessage);
        //redis 저장
        redisChatRepository.chatMessageSave(ChatMessageDto.from(chatMessage));
        //메시지 전송
        // TODO: roomId로 방 주소를 구분하면 주소만 가지고 다른방에 채팅을 할 수 있음. 따라서 암호화 필요 (UUID?)
        redisPublisher.publish(redisChatRepository.getTopic(command.getRoomId()), ChatMessageDto.from(chatMessage));
        //접속하지 않은 유저 unread message 추가하기
        for (Long offlineMemberId : offlineMembers) {
            User offlineUser = userRepository.findById(offlineMemberId).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저"));
            UnreadMessage unreadMessage = UnreadMessage.createUnreadMessage(
                    chatRoom,
                    offlineUser,
                    chatMessage
            );
            //mysql 저장
//            offlineUser.getUnreadMessages().add(unreadMessage);
            UnreadMessage savedUnreadMessage = unreadMessageRepository.save(unreadMessage);
            //redis 저장
            redisChatRepository.addUnreadMessage(UnreadMessageDto.from(savedUnreadMessage));
        }
        return chatMessage.getId();
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
        newChatRoom.getChatUsers().get(0).connectRoom();//방 주인 접속상태 업데이트
        //mysql 등록
        ChatRoom savedChatRoom = chatRoomRepository.save(newChatRoom);
        Long roomId = savedChatRoom.getId();

        //redis 등록
        redisChatRepository.createChatRoom(savedChatRoom);
        redisChatRepository.enterChatRoom(roomId);
        //생성메시지 발행
        CreatePubMessageCommand pubCreateMessageCommand = CreatePubMessageCommand.builder()
                .messageType("TALK")
                .messageText("채팅방이 시작되었습니다.")
                .roomId(roomId)
                .userId(1L)
                .build();
        sendMessage(pubCreateMessageCommand);
        //초대됨 메시지 발행
        for (User user : command.getUsers()) {
            CreatePubMessageCommand pubInviteMessageCommand = CreatePubMessageCommand.builder()
                    .messageType("ENTER")
                    .roomId(roomId)
                    .userId(user.getId())
                    .build();
            sendMessage(pubInviteMessageCommand);
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
        ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);
        //redis 저장
        redisChatRepository.inviteChatUsers(savedChatRoom, command.getUsers());
//        redisChatRepository.updateMemberCount(command.getRoomId(), command.getUsers().size());
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
    public List<ListChatRoomDto> listMyChatRoom(ListMyChatRoomCommand command) {
        List<ListChatRoomDto> chatRoomDtoList = new ArrayList<>();
        List<ChatRoom> myChatRooms = chatRoomRepository.findMyChatRoomByUserId(command.getUserId());
        for (ChatRoom chatRoom : myChatRooms) {
            //채팅 참여한 유저 정보 할당
            List<User> chatUsers = chatUserRepository.findAllByChatRoom(chatRoom); //TODO: 이미 들어있는지 확인하고 있으면 빼버리기
            //last 채팅메시지 할당
            ChatMessage firstChatMessage = chatMessageRepository.findTop1ByChatRoomOrderByCreatedAtDesc(chatRoom).orElseThrow(() -> new EntityNotFoundException("채팅방에 message가 존재하지 않음"));
            ChatMessageDto firstChatMessageDto = ChatMessageDto.from(firstChatMessage);
            // unreadMessageCount 할당
            int unreadMessageCount = redisChatRepository.getUnreadMessageCount(chatRoom.getId(), command.getUserId());
            //dto 삽입
            ListChatRoomDto chatRoomDto = ListChatRoomDto.from(chatRoom);
            chatRoomDto.setChatUsers(chatUsers);
            chatRoomDto.setFirstChatMessage(firstChatMessageDto);
            chatRoomDto.setUnreadMessageCount(unreadMessageCount);
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

    public void connectRoom(Long roomId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저"));
        ChatUser chatUser = chatRoomRepository.findChatUserByIdAndUser(roomId, userId).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 채팅유저"));
        chatUser.connectRoom();
        //redis 접속상태 등록
        redisChatRepository.connectRoom(roomId, chatUser.getId());
        //unreadmessage 제거
        List<UnreadMessageDto> deletedUnreadMessages = redisChatRepository.deleteUnreadMessage(roomId, userId); //redis
        for (UnreadMessageDto unreadMessageDto : deletedUnreadMessages) { //mysql
            unreadMessageRepository.deleteById(unreadMessageDto.getId());
            //unreadCount update
            ChatMessage chatMessage = chatMessageRepository.findById(unreadMessageDto.getChatMessageId()).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 메시지"));
            chatMessage.updateUnreadCount();
            redisChatRepository.updateChatMessageUnreadCount(unreadMessageDto);
        }
        //다른 채팅방 유저에게 채팅 목록 최신화 요청
        CreatePubMessageCommand pubMessageCommand = CreatePubMessageCommand.builder()
                .messageType("OPER")
                .roomId(roomId)
                .userId(userId)
                .build();
        sendMessage(pubMessageCommand);
    }

    public void disconnectRoom(Long roomId, Long userId) {
        ChatUser chatUser = chatRoomRepository.findChatUserByIdAndUser(roomId, userId).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 채팅유저"));
        chatUser.disconnectRoom();
        //redis 접속해제상태 등록
        redisChatRepository.disconnectRoom(roomId, chatUser.getId());
    }
}