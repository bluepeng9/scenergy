package com.wbm.scenergyspring.domain.chat.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wbm.scenergyspring.domain.chat.dto.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

/**
 * sub 서비스
 * 메시지가 발행되면 메시지를 읽어 처리하는 리스너
 */
@Component
@RequiredArgsConstructor
public class RedisSubscriber {

    final ObjectMapper objectMapper;
    final SimpMessageSendingOperations messagingTemplate;

    /**
     * redisMessageListener가 message를 받으면 실행됨 (redis config)
     */
    public void sendMessage(String publishMessage) {
        ChatMessageDto chatMessage = null;
        try {
            chatMessage = objectMapper.readValue(publishMessage, ChatMessageDto.class);
            messagingTemplate.convertAndSend("/sub/chat/room/" + chatMessage.getChatRoomId(), chatMessage);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}