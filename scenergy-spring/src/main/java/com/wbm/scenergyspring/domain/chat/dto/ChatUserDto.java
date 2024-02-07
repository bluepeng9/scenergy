package com.wbm.scenergyspring.domain.chat.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.wbm.scenergyspring.domain.chat.entity.ChatUser;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
public class ChatUserDto implements Serializable {
    private Long id;
    private Long chatRoomId;
    private Long userId;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime created_at;

    public static ChatUserDto from(ChatUser chatUser) {
        return ChatUserDto.builder()
                .id(chatUser.getId())
                .chatRoomId(chatUser.getChatRoom().getId())
                .userId(chatUser.getUser().getId())
                .created_at(chatUser.getCreatedAt())
                .build();
    }
}