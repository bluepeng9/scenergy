package com.wbm.scenergyspring.domain.chat.service;

import com.wbm.scenergyspring.domain.chat.entity.ChatMessage;
import com.wbm.scenergyspring.domain.chat.entity.ChatRoom;
import com.wbm.scenergyspring.domain.chat.entity.ChatUser;
import com.wbm.scenergyspring.domain.chat.repository.ChatMessageRepository;
import com.wbm.scenergyspring.domain.chat.repository.ChatRoomRepository;
import com.wbm.scenergyspring.domain.chat.repository.ChatUserRepository;
import com.wbm.scenergyspring.domain.chat.service.command.CreateChatRoomCommand;
import com.wbm.scenergyspring.domain.chat.service.command.CreatePubMessageCommand;
import com.wbm.scenergyspring.domain.user.entity.User;
import com.wbm.scenergyspring.domain.user.repository.UserRepository;
import com.wbm.scenergyspring.global.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatService {

    final ChatRoomRepository chatRoomRepository;
    final ChatMessageRepository chatMessageRepository;
    final ChatUserRepository chatUserRepository;
    final UserRepository userRepository;
    final SimpMessagingTemplate simpMessagingTemplate;

    @Transactional(readOnly = false)
    public Long createChatRoom(CreateChatRoomCommand command) {
        if (command.getStatus() == 0) {
            //TODO: 같은 스테이터스를 가지고 챗 맴버도 동일한 방을 찾아봐야함
        }
        ChatRoom newChatRoom = ChatRoom.createNewRoom(
                command.getName(),
                command.getStatus(),
                command.getChatUsers()
        );
        return chatRoomRepository.save(newChatRoom).getId();
    }

    /**
     * 메시지 전송
     *
     * @param command: userId, roomId, messageText, messageType
     * @return MessageId (0L: 채팅방 삭제)
     */
    @Transactional(readOnly = false)
    public Long sendMessage(CreatePubMessageCommand command) {
        ChatRoom chatRoom = chatRoomRepository.findById(command.getRoomId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 채팅룸"));
        User user = userRepository.findById(command.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저"));
        ChatUser chatUser = chatUserRepository.findByChatRoomAndUser(chatRoom, user)
                .orElse(ChatUser.createChatUser(chatRoom, user));

        String messageType = command.getMessageType();
        if (messageType.equals("ENTER")) {
            command.setMessageText(user.getName() + "님이 입장하셨습니다.");
            chatUser.joinRoom();
        } else if (messageType.equals("EXIT")) {
            command.setMessageText(user.getName() + "님이 퇴장하셨습니다.");
            int remainingMembersCount = chatUser.leaveRoom();
            if (remainingMembersCount <= 0) { // 채팅방 삭제
                chatRoomRepository.delete(chatRoom);
                return 0L;
            }
        }
        ChatMessage chatMessage = ChatMessage.createChatMessage(
                command.getUserId(),
                command.getMessageText(),
                chatRoom
        );

        chatRoom.addChatMessages(chatMessage);
        //chatRoom을 통하여 save할 것인지 chatMessage를 통하여 save할 것인지?
        //message pub(ws://localhost:8080/ws/sub/chat/{roomId})
        simpMessagingTemplate.convertAndSend("/sub/chat/" + command.getRoomId(), chatMessage);
        // TODO: roomId로 방 주소를 구분하면 주소만 가지고 다른방에 채팅을 할 수 있음. 따라서 암호화 필요 (UUID?)
        return chatMessageRepository.save(chatMessage).getId();
    }
}
