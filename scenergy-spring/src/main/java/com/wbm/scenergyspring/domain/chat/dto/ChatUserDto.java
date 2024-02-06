package com.wbm.scenergyspring.domain.chat.dto;

import com.wbm.scenergyspring.domain.chat.entity.ChatRoom;
import com.wbm.scenergyspring.domain.chat.entity.ChatUser;
import com.wbm.scenergyspring.domain.user.entity.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ChatUserDto {
    private Long id;
    private ChatRoom chatRoom;
    private User user;
    private LocalDateTime created_at;

    public static ChatUserDto from(ChatUser chatUser) {
        return ChatUserDto.builder()
                .id(chatUser.getId())
                .chatRoom(chatUser.getChatRoom())
                .user(chatUser.getUser())
                .created_at(chatUser.getCreatedAt())
                .build();
    }
}