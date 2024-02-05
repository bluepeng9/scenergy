package com.wbm.scenergyspring.domain.chat.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.wbm.scenergyspring.domain.chat.entity.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDto {
    private Long id;
    private Long senderId;
    private String messageText;
    private int unreadCount;
    private Long chatRoomId;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdAt;
    private int operationCode;

    public static ChatMessageDto from(ChatMessage chatMessage) {
        return ChatMessageDto.builder()
                .id(chatMessage.getId())
                .senderId(chatMessage.getSenderId())
                .messageText(chatMessage.getMessageText())
                .unreadCount(chatMessage.getUnreadCount())
                .chatRoomId(chatMessage.getChatRoom().getId())
                .createdAt(chatMessage.getCreatedAt())
                .build();
    }

    public void updateUnreadCount() {
        unreadCount--;
    }

    /**
     * ws을 통한 system 동작을 위한 code
     *
     * @param code 1: need chatroom message update
     */
    public void putOperationCode(int code) {
        operationCode = code;
    }
}