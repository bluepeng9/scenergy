package com.wbm.scenergyspring.domain.chat.dto;

import com.wbm.scenergyspring.domain.chat.entity.UnreadMessage;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class UnreadMessageDto implements Serializable {
    private Long id;
    private Long chatRoomId;
    private Long userId;
    private Long chatMessageId;

    public static UnreadMessageDto from(UnreadMessage unreadMessage) {
        return UnreadMessageDto.builder()
                .id(unreadMessage.getId())
                .chatRoomId(unreadMessage.getChatRoom().getId())
                .userId(unreadMessage.getUser().getId())
                .chatMessageId(unreadMessage.getChatMessage().getId())
                .build();
    }
}