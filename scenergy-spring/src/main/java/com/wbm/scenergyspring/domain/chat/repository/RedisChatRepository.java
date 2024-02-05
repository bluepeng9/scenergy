package com.wbm.scenergyspring.domain.chat.repository;

import com.wbm.scenergyspring.domain.chat.dto.ChatMessageDto;
import com.wbm.scenergyspring.domain.chat.dto.ChatOnlineInfoDto;
import com.wbm.scenergyspring.domain.chat.dto.RedisChatRoomDto;
import com.wbm.scenergyspring.domain.chat.dto.UnreadMessageDto;
import com.wbm.scenergyspring.domain.chat.entity.ChatOnlineInfo;
import com.wbm.scenergyspring.domain.chat.entity.ChatRoom;
import com.wbm.scenergyspring.domain.chat.entity.ChatUser;
import com.wbm.scenergyspring.domain.chat.redis.RedisSubscriber;
import com.wbm.scenergyspring.domain.user.entity.User;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class RedisChatRepository {
    //채팅방에 발행되는 메시지를 처리할 listener
    private final RedisMessageListenerContainer redisMessageListenerContainer;
    //구독 처리
    private final RedisSubscriber redisSubscriber;
    //redis
    private static final String CHAT_ROOMS = "CHAT_ROOM";
    private static final String ROOM_ONLINE_MEMBER = "ROOM_ONLINE_MEMBER";
    private static final String UNREAD_MESSAGE = "UNREAD_MESSAGE";
    private final RedisTemplate<String, ChatMessageDto> redisTemplateMessage;
    //operators
    @Resource(name = "redisTemplateChatRoom")
    private HashOperations<String, String, RedisChatRoomDto> opsHashChatRoom;
    @Resource(name = "redisTemplateUnreadMessage")
    private HashOperations<String, String, UnreadMessageDto> opsHashUnreadMessage;
    @Resource(name = "redisTemplateOnlineMember")
    private HashOperations<String, String, ChatOnlineInfoDto> opsHashOnlineMember;
    @Resource(name = "redisTemplateMessage")
    private ListOperations<String, ChatMessageDto> opsListChatMessage;
    @Resource(name = "redisTemplateMessageIndex")
    private HashOperations<String, Object, Object> opsHashChatMessageIndex;

    //채팅방에 대화메시지를 발행하기 위한 redis topic 정보. 서버별로 채팅방에 매치되는 topic 정보를 map에 넣어 roomId로 찾을 수 있도록 한다.
    private Map<String, ChannelTopic> topics;

    @PostConstruct
    private void init() {
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
        RedisChatRoomDto chatRoomDto = opsHashChatRoom.get(CHAT_ROOMS, strRoomId);
        chatRoomDto.updateRoomName(name);
        opsHashChatRoom.put(CHAT_ROOMS, Long.toString(chatRoomDto.getId()), chatRoomDto);
        return roomId;
    }

    public int updateMemberCount(Long roomId, int count) {
        String strRoomId = Long.toString(roomId);
        RedisChatRoomDto chatRoomDto = opsHashChatRoom.get(CHAT_ROOMS, strRoomId);
        chatRoomDto.updateMemberCount(count);
        if (chatRoomDto.getChatUsersCount() <= 0) {
            opsHashChatRoom.delete(CHAT_ROOMS, strRoomId);
        } else {
            opsHashChatRoom.put(CHAT_ROOMS, strRoomId, chatRoomDto);
        }
        return chatRoomDto.getChatUsersCount();
    }

    public void inviteChatUsers(ChatRoom chatRoom, List<User> users) {
        String strRoomId = Long.toString(chatRoom.getId());
        RedisChatRoomDto redisChatRoomDto = opsHashChatRoom.get(CHAT_ROOMS, strRoomId);
        if (redisChatRoomDto != null) {
            //redis room update
            opsHashChatRoom.delete(CHAT_ROOMS, strRoomId);
            opsHashChatRoom.put(CHAT_ROOMS, strRoomId, RedisChatRoomDto.from(chatRoom));
            //redis onlineMember update
            for (int i = chatRoom.getChatUsers().size() - users.size(); i < chatRoom.getChatUsers().size(); i++) {
                ChatUser invitedChatUser = chatRoom.getChatUsers().get(i);
                String strChatUserId = Long.toString(invitedChatUser.getId());
                opsHashOnlineMember.put(ROOM_ONLINE_MEMBER + strRoomId, strChatUserId, ChatOnlineInfoDto.from(invitedChatUser.getChatOnlineInfo()));
            }
        }
    }

    public void createChatRoom(ChatRoom chatRoom) {
        String strRoomId = Long.toString(chatRoom.getId());
        //채팅방 등록
        opsHashChatRoom.put(CHAT_ROOMS, strRoomId, RedisChatRoomDto.from(chatRoom));
        //채팅 맴버 online table 등록
        for (ChatUser chatUser : chatRoom.getChatUsers()) {
            String strChatUserId = Long.toString(chatUser.getId());
            ChatOnlineInfo chatOnlineInfo = chatUser.getChatOnlineInfo();
            opsHashOnlineMember.put(ROOM_ONLINE_MEMBER + strRoomId, strChatUserId, ChatOnlineInfoDto.from(chatOnlineInfo));
        }
    }

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
        //redis 저장
        opsListChatMessage.rightPush(strRoomId, chatMessage);
        //채팅 index 저장
        Long index = redisTemplateMessage.opsForList().size(strRoomId) - 1L;
        opsHashChatMessageIndex.put(strRoomId, chatMessage.getId(), index);
    }

    /**
     * redis에 저장된 메시지를 가져오는 함수
     *
     * @param lastMessage : 현재 보이는 마지막 message
     * @return
     */
    public List<ChatMessageDto> loadChatMessage(ChatMessageDto lastMessage) {
        String strRoomId = Long.toString(lastMessage.getChatRoomId());
        Long index = (Long) opsHashChatMessageIndex.get(strRoomId, lastMessage.getId()) - 1L;
        if (index < 0) {
            return new ArrayList<ChatMessageDto>();
        }
        long searchStart = (index - 99 <= 0) ? 0 : index - 99;
        List<ChatMessageDto> messageList = opsListChatMessage.range(strRoomId, searchStart, index);
        Collections.reverse(messageList);
        return messageList;
    }
    //TODO: invite시 onlineInfo추가 할 것

    public void connectRoom(Long roomId, Long chatUserId) {
        String strRoomId = Long.toString(roomId);
        String strChatUserId = Long.toString(chatUserId);
        ChatOnlineInfoDto chatOnlineInfoDto = opsHashOnlineMember.get(ROOM_ONLINE_MEMBER + strRoomId, strRoomId);
        if (chatOnlineInfoDto != null) { //update
            chatOnlineInfoDto.setOnlineStatus(true);
            opsHashOnlineMember.delete(ROOM_ONLINE_MEMBER + strRoomId, strRoomId);
            opsHashOnlineMember.put(ROOM_ONLINE_MEMBER + strRoomId, strChatUserId, chatOnlineInfoDto);
        }
    }

    public void disconnectRoom(Long roomId, Long chatUserId) {
        String strRoomId = Long.toString(roomId);
        String strChatUserId = Long.toString(chatUserId);
        ChatOnlineInfoDto chatOnlineInfoDto = opsHashOnlineMember.get(ROOM_ONLINE_MEMBER + strRoomId, strRoomId);
        if (chatOnlineInfoDto != null) { //update
            chatOnlineInfoDto.setOnlineStatus(false);
            opsHashOnlineMember.delete(ROOM_ONLINE_MEMBER + strRoomId, strRoomId);
            opsHashOnlineMember.put(ROOM_ONLINE_MEMBER + strRoomId, strChatUserId, chatOnlineInfoDto);
        }
    }

    public List<Long> findOfflineMember(RedisChatRoomDto chatRoomDto) {
        String strRoomId = Long.toString(chatRoomDto.getId());
        List<ChatOnlineInfoDto> onlineInfoDtos = opsHashOnlineMember.values(ROOM_ONLINE_MEMBER + strRoomId);
        List<Long> offlineMembers = new ArrayList<>();
        for (ChatOnlineInfoDto onlineInfoDto : onlineInfoDtos) {
            if (!onlineInfoDto.getOnlineStatus()) {
                offlineMembers.add(onlineInfoDto.getUserId());
            }
        }
        return offlineMembers;
    }

    /**
     * 메시지 전송 요청이 들어왔을때 미접속 유저에게 unreadMessage 추가
     *
     * @param unreadMessageDto: 추가할 message
     */
    public void addUnreadMessage(UnreadMessageDto unreadMessageDto) {
        String strRoomId = Long.toString(unreadMessageDto.getChatRoomId());
        String strChatId = Long.toString(unreadMessageDto.getChatMessageId());
        //offline member search
        List<ChatOnlineInfoDto> onlineInfoDtos = opsHashOnlineMember.values(ROOM_ONLINE_MEMBER + strRoomId);
        for (ChatOnlineInfoDto onlineInfoDto : onlineInfoDtos) {
            if (!onlineInfoDto.getOnlineStatus()) {
                String strUserId = Long.toString(onlineInfoDto.getUserId());
                opsHashUnreadMessage.put(strUserId + UNREAD_MESSAGE + strRoomId, strChatId, unreadMessageDto);
            }
        }
    }

    public List<UnreadMessageDto> deleteUnreadMessage(Long roomId, Long userId) {
        String strRoomId = Long.toString(roomId);
        String strUserId = Long.toString(userId);
        List<UnreadMessageDto> unreadMessageDtos = opsHashUnreadMessage.values(strUserId + UNREAD_MESSAGE + strRoomId);
        opsHashUnreadMessage.delete(strUserId + UNREAD_MESSAGE + strRoomId);
        return unreadMessageDtos;
    }

    public int getUnreadMessageCount(Long roomId, Long userId) {
        String strRoomId = Long.toString(roomId);
        String strUserId = Long.toString(userId);
        return opsHashUnreadMessage.values(strUserId + UNREAD_MESSAGE + strRoomId).size();
    }
}