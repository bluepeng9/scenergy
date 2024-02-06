package com.wbm.scenergyspring.domain.chat.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wbm.scenergyspring.domain.chat.dto.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

/**
 * sub 서비스
 * 메시지가 발행되면 메시지를 읽어 처리하는 리스너
 */
@Component
@RequiredArgsConstructor
public class RedisSubscriber implements MessageListener {
    final ObjectMapper objectMapper;
    final RedisTemplate redisTemplate;
    final SimpMessageSendingOperations messagingTemplate;

    /**
     * redis message 전송 method
     * @param message
     * @param pattern
     */
    @SneakyThrows // readValue 부터 오는 throw
    @Override // publish되면 작동하는 메서드
    public void onMessage(Message message, byte[] pattern) {
        //redis에서 발행된 데이터를 받아 deserialize
        String publishMessage = (String) redisTemplate.getStringSerializer().deserialize(message.getBody());
        ChatMessageDto chatMessage = objectMapper.readValue(publishMessage, ChatMessageDto.class);
        messagingTemplate.convertAndSend("/sub/chat/room/" + chatMessage.getChatRoomId(), chatMessage);
    }
}