package com.wbm.scenergyspring.domain.chat.redis;

import com.wbm.scenergyspring.domain.chat.entity.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

/**
 * 채팅 발행 서비스
 */
@Component
@RequiredArgsConstructor
public class RedisPublisher {
    private final RedisTemplate<String, Object> redisTemplate;
    /**
     * 채팅 발행 method
     * @param topic   발행할 topic
     * @param message 발행할 chat
     */
    public void publish(ChannelTopic topic, ChatMessageDto message) {
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }
}
