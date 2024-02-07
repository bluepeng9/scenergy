package com.wbm.scenergyspring.domain.chat.repository;

import com.wbm.scenergyspring.domain.chat.dto.*;
import com.wbm.scenergyspring.domain.chat.entity.ChatOnlineInfo;
import com.wbm.scenergyspring.domain.chat.entity.ChatRoom;
import com.wbm.scenergyspring.domain.chat.entity.ChatUser;
import com.wbm.scenergyspring.domain.user.entity.User;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class RedisChatRepository {
    //redis
    private static final String CHAT_ROOMS = "CHAT_ROOM";
    private static final String CHAT_MESSAGE = "CHAT_MESSAGE";
    private static final String ROOM_ONLINE_MEMBER = "ROOM_ONLINE_MEMBER";
    private static final String UNREAD_MESSAGE = "UNREAD_MESSAGE";
    private static final String SESSION_CHATUSER = "SESSION_CHATUSER";
    //operators
    private final RedisTemplate<String, ChatMessageDto> redisTemplateMessage;
    @Resource(name = "redisTemplateSessionChatUser")
    private HashOperations<String, String, ChatUserDto> opsHashSessionChatUser;
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

    @PostConstruct
    private void init() {
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

    public void chatMessageSave(ChatMessageDto chatMessage) {
        String strRoomId = Long.toString(chatMessage.getChatRoomId());
        redisTemplateMessage.setValueSerializer(new Jackson2JsonRedisSerializer<>(ChatMessageDto.class));
        //redis 저장
        opsListChatMessage.rightPush(strRoomId, chatMessage);
        //채팅 index 저장
        Long index = redisTemplateMessage.opsForList().size(strRoomId) - 1L;
        opsHashChatMessageIndex.put(CHAT_MESSAGE + strRoomId, chatMessage.getId(), index);
    }

    /**
     * redis에 저장된 메시지를 가져오는 함수
     *
     * @param lastMessage : 현재 보이는 마지막 message
     * @return
     */
    public List<ChatMessageDto> loadChatMessage(ChatMessageDto lastMessage) {
        String strRoomId = Long.toString(lastMessage.getChatRoomId());
        Long index = (Long) opsHashChatMessageIndex.get(CHAT_MESSAGE + strRoomId, lastMessage.getId());
        if (index < 0) {
            return new ArrayList<ChatMessageDto>();
        }
        long searchStart = (index - 99 <= 0) ? 0 : index - 99;
        List<ChatMessageDto> messageList = opsListChatMessage.range(strRoomId, searchStart, index - 1L);
        Collections.reverse(messageList);
        return messageList;
    }

    public void updateChatMessageUnreadCount(UnreadMessageDto unreadMessageDto) {
        String strRoomId = Long.toString(unreadMessageDto.getChatRoomId());
        String strMessageId = Long.toString(unreadMessageDto.getChatMessageId());
        Long index = (Long) opsHashChatMessageIndex.get(CHAT_MESSAGE + strRoomId, unreadMessageDto.getChatMessageId());
        ChatMessageDto saveChatMessage = opsListChatMessage.index(strRoomId, index);
        saveChatMessage.updateUnreadCount();
        opsListChatMessage.set(strRoomId, index, saveChatMessage);
    }

    public void connectRoom(Long roomId, Long chatUserId) {
        String strRoomId = Long.toString(roomId);
        String strChatUserId = Long.toString(chatUserId);
        ChatOnlineInfoDto chatOnlineInfoDto = opsHashOnlineMember.get(ROOM_ONLINE_MEMBER + strRoomId, strChatUserId);
        if (chatOnlineInfoDto != null) { //update
            chatOnlineInfoDto.setOnlineStatus(true);
            opsHashOnlineMember.put(ROOM_ONLINE_MEMBER + strRoomId, strChatUserId, chatOnlineInfoDto);
        }
    }

    public void disconnectRoom(Long roomId, Long chatUserId) {
        String strRoomId = Long.toString(roomId);
        String strChatUserId = Long.toString(chatUserId);
        ChatOnlineInfoDto chatOnlineInfoDto = opsHashOnlineMember.get(ROOM_ONLINE_MEMBER + strRoomId, strChatUserId);
        if (chatOnlineInfoDto != null) { //update
            chatOnlineInfoDto.setOnlineStatus(false);
            opsHashOnlineMember.put(ROOM_ONLINE_MEMBER + strRoomId, strChatUserId, chatOnlineInfoDto);
        }
    }

    public List<Long> getOfflineMembers(RedisChatRoomDto chatRoomDto) {
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

    public List<Long> getOnlineMembers(RedisChatRoomDto chatRoomDto) {
        String strRoomId = Long.toString(chatRoomDto.getId());
        List<ChatOnlineInfoDto> onlineInfoDtos = opsHashOnlineMember.values(ROOM_ONLINE_MEMBER + strRoomId);
        List<Long> onlineMembers = new ArrayList<>();
        for (ChatOnlineInfoDto onlineInfoDto : onlineInfoDtos) {
            if (onlineInfoDto.getOnlineStatus()) {
                onlineMembers.add(onlineInfoDto.getUserId());
            }
        }
        return onlineMembers;
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
        opsHashUnreadMessage.entries(strUserId + UNREAD_MESSAGE + strRoomId).keySet()
                .forEach(
                        haskKey -> opsHashUnreadMessage.delete(strUserId + UNREAD_MESSAGE + strRoomId, haskKey)
                );
        return unreadMessageDtos;
    }

    public int getUnreadMessageCount(Long roomId, Long userId) {
        String strRoomId = Long.toString(roomId);
        String strUserId = Long.toString(userId);
        return opsHashUnreadMessage.values(strUserId + UNREAD_MESSAGE + strRoomId).size();
    }

    public void saveChatUserBySessionId(String simpSessionId, ChatUserDto chatUser) {
        opsHashSessionChatUser.put(SESSION_CHATUSER, simpSessionId, chatUser);
        log.info("saveChatUserBySessionId");
        log.info("simpSessionId: " + simpSessionId + " chatUser: " + chatUser);
        List<ChatUserDto> values = opsHashSessionChatUser.values(SESSION_CHATUSER);
        for (ChatUserDto value : values) {
            log.info("value: " + value);
        }
    }


    public ChatUserDto findChatUserBySessionId(String simpSessionId) {
        ChatUserDto chatUserDto = opsHashSessionChatUser.get(SESSION_CHATUSER, simpSessionId);
        log.info("findChatUserBySessionId");
        log.info("simpSessionId: " + simpSessionId + " chatUser: " + chatUserDto);
        return chatUserDto;
    }
}