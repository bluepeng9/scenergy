package com.wbm.scenergyspring.domain.chat.dto;

import com.wbm.scenergyspring.domain.chat.entity.ChatMessage;
import com.wbm.scenergyspring.domain.chat.entity.ChatRoom;
import com.wbm.scenergyspring.domain.chat.entity.UnreadMessage;
import com.wbm.scenergyspring.domain.user.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UnreadMessageDto {
    private Long id;
    private ChatRoom chatRoom;
    private User user;
    private ChatMessage chatMessage;

    public static UnreadMessageDto from(UnreadMessage unreadMessage) {
        return UnreadMessageDto.builder()
                .id(unreadMessage.getId())
                .chatRoom(unreadMessage.getChatRoom())
                .user(unreadMessage.getUser())
                .chatMessage(unreadMessage.getChatMessage())
                .build();
    }
}