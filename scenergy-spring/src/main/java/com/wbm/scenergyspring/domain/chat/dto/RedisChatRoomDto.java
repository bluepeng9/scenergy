package com.wbm.scenergyspring.domain.chat.dto;

import com.wbm.scenergyspring.domain.chat.entity.ChatRoom;
import com.wbm.scenergyspring.domain.chat.entity.ChatUser;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class RedisChatRoomDto implements Serializable {
    private static final long serialVersionUID = 32112411231245123L;
    private Long id;
    private String name;
    private int status;
    private ChatMessageDto firstChatMessage;
    private int chatUsersCount;
    private List<Long> chatUserIds;

    public static RedisChatRoomDto from(ChatRoom chatRoom) {
        List<Long> chatUserIds = new ArrayList<>();
        for (ChatUser chatUser : chatRoom.getChatUsers()) {
            chatUserIds.add(chatUser.getId());
        }
        return RedisChatRoomDto.builder()
                .id(chatRoom.getId())
                .name(chatRoom.getName())
                .status(chatRoom.getStatus())
                .chatUsersCount(chatRoom.getChatUsers().size())
                .chatUserIds(chatUserIds)
                .build();
    }

    public void updateRoomName(String name) {
        this.name = name;
    }

    public void updateMemberCount(int count) {
        this.chatUsersCount += count;
    }
}
