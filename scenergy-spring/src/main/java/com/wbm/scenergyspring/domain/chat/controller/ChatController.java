package com.wbm.scenergyspring.domain.chat.controller;

import com.wbm.scenergyspring.domain.chat.entity.ChatMessage;
import com.wbm.scenergyspring.domain.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    /**
     * 현재 ws://localhost:8080/ws/pub/chat
     */
    @MessageMapping("/chat")
    public void pubMessage(ChatMessage chatMessage) {
        log.info(chatMessage.toString());
        chatService.sendMessage(chatMessage);
    }
}
