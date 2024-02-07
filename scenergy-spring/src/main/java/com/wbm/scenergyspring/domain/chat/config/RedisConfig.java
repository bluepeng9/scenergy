package com.wbm.scenergyspring.domain.chat.config;

import com.wbm.scenergyspring.domain.chat.dto.*;
import com.wbm.scenergyspring.domain.chat.redis.RedisSubscriber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
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
     * 단일 Topic 사용을 위한 bean 설정
     * 메시지를 보낼 채팅방 url을 사용하여 전송하기에 topic을 각각 생성하고 listener를 만들어줄 필요가 없음
     */
    @Bean
    public ChannelTopic channelTopic() {
        return new ChannelTopic("chatroom");
    }

    /**
     * 메시지를 구독자에게 보내는 역할
     */
    @Bean
    public MessageListenerAdapter listenerAdapter(RedisSubscriber subscriber) {
        return new MessageListenerAdapter(subscriber, "sendMessage");
    }


    /**
     * redis pub/sub message 처리 listener 설정
     */
    @Bean
    public RedisMessageListenerContainer redisMessageListener(
            RedisConnectionFactory connectionFactory,
            MessageListenerAdapter listenerAdapter,
            ChannelTopic channelTopic
    ) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, channelTopic);
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
    public RedisTemplate<String, UnreadMessageDto> redisTemplateUnreadMessage(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, UnreadMessageDto> redisTemplateUnreadMessage = new RedisTemplate<>();
        redisTemplateUnreadMessage.setConnectionFactory(redisConnectionFactory);
        redisTemplateUnreadMessage.setKeySerializer(new StringRedisSerializer());
        redisTemplateUnreadMessage.setValueSerializer(new Jackson2JsonRedisSerializer<>(UnreadMessageDto.class));
        return redisTemplateUnreadMessage;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplateSessionChatUser(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplateSessionChatUser = new RedisTemplate<>();
        redisTemplateSessionChatUser.setConnectionFactory(redisConnectionFactory);
        redisTemplateSessionChatUser.setKeySerializer(new StringRedisSerializer());
        redisTemplateSessionChatUser.setValueSerializer(new Jackson2JsonRedisSerializer<>(ChatUserDto.class));
        return redisTemplateSessionChatUser;
    }
}
