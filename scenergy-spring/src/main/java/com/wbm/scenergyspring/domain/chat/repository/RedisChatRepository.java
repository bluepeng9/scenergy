package com.wbm.scenergyspring.domain.chat.repository;

import com.wbm.scenergyspring.domain.chat.dto.ChatMessageDto;
import com.wbm.scenergyspring.domain.chat.dto.RedisChatRoomDto;
import com.wbm.scenergyspring.domain.chat.redis.RedisSubscriber;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class RedisChatRepository {
    //채팅방에 발행되는 메시지를 처리할 listener
    private final RedisMessageListenerContainer redisMessageListenerContainer;
    //구독 처리
    private final RedisSubscriber redisSubscriber;
    //redis
    private static final String CHAT_ROOMS = "CHAT_ROOM";
    private final RedisTemplate<String, ChatMessageDto> redisTemplateMessage;
    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, RedisChatRoom> opsHashChatRoom;
    //채팅방에 대화메시지를 발행하기 위한 redis topic 정보. 서버별로 채팅방에 매치되는 topic 정보를 map에 넣어 roomId로 찾을 수 있도록 한다.
    private Map<String, ChannelTopic> topics;

    @PostConstruct
    private void init() {
        opsHashChatRoom = redisTemplate.opsForHash();
        topics = new HashMap<>();
    }

    public List<RedisChatRoomDto> findAllRoom() {
        return opsHashChatRoom.values(CHAT_ROOMS);
    }

    public RedisChatRoomDto findRoomById(String id) {
        return opsHashChatRoom.get(CHAT_ROOMS, id);
    }

    public Long renameChatRoom(Long roomId, String name) {
        String strRoomId = Long.toString(roomId);
        RedisChatRoomDto redisChatRoomDto = opsHashChatRoom.get(CHAT_ROOMS, strRoomId);
        redisChatRoomDto.updateRoomName(name);
        opsHashChatRoom.put(CHAT_ROOMS, Long.toString(redisChatRoomDto.getRoomId()), redisChatRoomDto);
        return roomId;
    }

    public int updateMemberCount(Long roomId, int count) {
        String strRoomId = Long.toString(roomId);
        RedisChatRoomDto redisChatRoomDto = opsHashChatRoom.get(CHAT_ROOMS, strRoomId);
        redisChatRoomDto.updateMemberCount(count);
        if (redisChatRoomDto.getMemberCount() <= 0) {
            opsHashChatRoom.delete(CHAT_ROOMS, strRoomId);
        } else {
            opsHashChatRoom.put(CHAT_ROOMS, Long.toString(redisChatRoomDto.getRoomId()), redisChatRoomDto);
        }
        return redisChatRoomDto.getMemberCount();
    }

    public RedisChatRoomDto createChatRoom(Long roomId, String name, int status, int memberCount) {
        RedisChatRoomDto redisChatRoomDto = RedisChatRoomDto.createRedisChatRoom(roomId, name, status, memberCount);
        opsHashChatRoom.put(CHAT_ROOMS, Long.toString(redisChatRoomDto.getRoomId()), redisChatRoomDto);
        return redisChatRoomDto;
    }

    // ------- 위는 redis를 사용하는 chatRoom crud(mysql로 관리한다면 안사용해도 괜찮을 듯?)

    //topic을 만들고 listener 설정
    public void enterChatRoom(Long roomId) {
        String strRoomId = Long.toString(roomId);
        ChannelTopic topic = topics.get(strRoomId);
        if (topic == null) {
            topic = new ChannelTopic(strRoomId);
            redisMessageListenerContainer.addMessageListener(redisSubscriber, topic);
            topics.put(strRoomId, topic);
        }
    }

    public ChannelTopic getTopic(Long roomId) {
        return topics.get(Long.toString(roomId));
    }

    public void chatMessageSave(ChatMessageDto chatMessage) {
        String strRoomId = Long.toString(chatMessage.getChatRoomId());
        redisTemplateMessage.setValueSerializer(new Jackson2JsonRedisSerializer<>(ChatMessageDto.class));
        redisTemplateMessage.opsForList().rightPush(strRoomId, chatMessage);
    }

    public List<ChatMessageDto> loadChatMessage(Long roomId) {
        String strRoomId = Long.toString(roomId);
        List<ChatMessageDto> messageList = redisTemplateMessage.opsForList().range(strRoomId, 0, 99);
        return messageList;
    }
}
