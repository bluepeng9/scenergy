package com.wbm.scenergyspring.domain.chat.service;

import com.wbm.scenergyspring.domain.chat.entity.ChatMessage;
import com.wbm.scenergyspring.domain.chat.entity.ChatRoom;
import com.wbm.scenergyspring.domain.chat.repository.ChatMessageRepository;
import com.wbm.scenergyspring.domain.chat.repository.ChatRoomRepository;
import com.wbm.scenergyspring.domain.chat.service.command.CreateChatRoomCommand;
import com.wbm.scenergyspring.domain.chat.service.command.CreatePubMessageCommand;
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

    final SimpMessagingTemplate simpMessagingTemplate;

    @Transactional(readOnly = false)
    public Long createChatRoom(CreateChatRoomCommand command) {
        if (command.getStatus() == 0) {
            //TODO: 같은 스테이터스를 가지고 챗 맴버도 동일한 방을 찾아봐야함
        }
        ChatRoom newChatRoom = ChatRoom.createNewRoom(command.getName(), command.getStatus(), command.getChatUsers());
        return chatRoomRepository.save(newChatRoom).getId();
    }

    public Long sendMessage(CreatePubMessageCommand command) {
        ChatRoom chatRoom = chatRoomRepository.findById(command.getRoomId()).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 채팅룸"));
        ChatMessage chatMessage = ChatMessage.createChatMessage(
                command.getUserId(),
                command.getMessageText(),
                chatRoom
        );
        //message pub(ws://localhost:8080/ws/sub/chat/{roomId})
        simpMessagingTemplate.convertAndSend("/sub/chat/" + command.getRoomId(), chatMessage);
        // TODO: roomId로 방 주소를 구분하면 주소만 가지고 다른방에 채팅을 할 수 있음. 따라서 암호화 필요 (UUID?)
        return chatMessageRepository.save(chatMessage).getId();
    }
}
