package com.wbm.scenergyspring.domain.chat.controller;

import com.wbm.scenergyspring.domain.chat.controller.request.PubMessageRequest;
import com.wbm.scenergyspring.domain.chat.controller.response.PubMessageResponse;
import com.wbm.scenergyspring.domain.chat.service.ChatService;
import com.wbm.scenergyspring.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class ChatController {

    private final ChatService chatService;

    /**
     * 현재 ws://localhost:8080/ws/pub/chat
     */
    @MessageMapping("/chat")
    public ResponseEntity<ApiResponse<PubMessageResponse>> pubMessage(@RequestBody PubMessageRequest request) {
        log.info("request: " + request.getMessage());
        Long chatId = chatService.sendMessage(request.toCreatePubMessageCommand());
        PubMessageResponse pubMessageResponse = PubMessageResponse.builder()
                .chatId(chatId)
                .build();
        return ResponseEntity.ok(ApiResponse.createSuccess(pubMessageResponse));
    }
}
