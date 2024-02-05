package com.wbm.scenergyspring.domain.chat.config;

import com.wbm.scenergyspring.domain.chat.dto.ChatMessageDto;
import com.wbm.scenergyspring.domain.chat.dto.ChatOnlineInfoDto;
import com.wbm.scenergyspring.domain.chat.dto.RedisChatRoomDto;
import com.wbm.scenergyspring.domain.chat.dto.UnreadMessageDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(host, port);
    }
    /**
     * redis pub/sub message 처리 listener 설정
     */
    @Bean
    public RedisMessageListenerContainer redisMessageListener(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        return container;
    }

    /**
     * redis db와 상호작용하는 template 설정
     * data 직렬화 실행
     */
    @Bean
    @Primary
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(String.class));
        return redisTemplate;
    }

    // Redis 에 메시지 내역을 저장하기 위한 RedisTemplate 을 설정
    @Bean
    public RedisTemplate<String, ChatMessageDto> redisTemplateMessage(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, ChatMessageDto> redisTemplateMessage = new RedisTemplate<>();
        redisTemplateMessage.setConnectionFactory(connectionFactory);
        redisTemplateMessage.setKeySerializer(new StringRedisSerializer());        // Key Serializer
        redisTemplateMessage.setValueSerializer(new Jackson2JsonRedisSerializer<>(String.class));      // Value Serializer
        return redisTemplateMessage;
    }

    @Bean
    public RedisTemplate<String, RedisChatRoomDto> redisTemplateChatRoom(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, RedisChatRoomDto> redisTemplateChatRoom = new RedisTemplate<>();
        redisTemplateChatRoom.setConnectionFactory(connectionFactory);
        redisTemplateChatRoom.setKeySerializer(new StringRedisSerializer());        // Key Serializer
        redisTemplateChatRoom.setValueSerializer(new Jackson2JsonRedisSerializer<>(RedisChatRoomDto.class));      // Value Serializer
        return redisTemplateChatRoom;
    }

    @Bean
    public RedisTemplate<?, ?> redisTemplateMessageIndex(RedisConnectionFactory connectionFactory) {
        RedisTemplate<?, ?> redisTemplateMessageIndex = new RedisTemplate<>();
        redisTemplateMessageIndex.setConnectionFactory(connectionFactory);
        return redisTemplateMessageIndex;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplateOnlineMember(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplateOnlineMember = new RedisTemplate<>();
        redisTemplateOnlineMember.setConnectionFactory(redisConnectionFactory);
        redisTemplateOnlineMember.setKeySerializer(new StringRedisSerializer());
        redisTemplateOnlineMember.setValueSerializer(new Jackson2JsonRedisSerializer<>(ChatOnlineInfoDto.class));
        return redisTemplateOnlineMember;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplateUnreadMessage(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplateUnreadMessage = new RedisTemplate<>();
        redisTemplateUnreadMessage.setConnectionFactory(redisConnectionFactory);
        redisTemplateUnreadMessage.setKeySerializer(new StringRedisSerializer());
        redisTemplateUnreadMessage.setValueSerializer(new Jackson2JsonRedisSerializer<>(UnreadMessageDto.class));
        return redisTemplateUnreadMessage;
    }
}
