package com.wbm.scenergyspring.domain.chat.service;

import com.wbm.scenergyspring.domain.chat.entity.ChatMessage;
import com.wbm.scenergyspring.domain.chat.entity.ChatRoom;
import com.wbm.scenergyspring.domain.chat.repository.ChatMessageRepository;
import com.wbm.scenergyspring.domain.chat.repository.ChatRoomRepository;
import com.wbm.scenergyspring.domain.chat.service.command.CreateChatRoomCommand;
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

    final SimpMessagingTemplate simpMessagingTemplate;

    @Transactional(readOnly = false)
    public Long createChatRoom(CreateChatRoomCommand command) {
        if (command.getStatus() == 0) {
            //TODO: 같은 스테이터스를 가지고 챗 맴버도 동일한 방을 찾아봐야함
        }
        ChatRoom newChatRoom = ChatRoom.createNewRoom(command.getName(), command.getStatus(), command.getChatUsers());
        return chatRoomRepository.save(newChatRoom).getId();
    }

    public void sendMessage(ChatMessage chatMessage) {
        simpMessagingTemplate.convertAndSend("/sub/chat``/" + chatMessage.getChatRoom().getId(), chatMessage);
        // 송신 주소 ws://localhost:8080/ws/sub/chat/{roomId}
        // TODO: roomId로 방 주소를 구분하면 주소만 가지고 다른방에 채팅을 할 수 있음. 따라서 암호화 필요 (UUID?)
        //메시지 보낸 후 메시지 저장
        chatMessageRepository.save(chatMessage);
    }
}
