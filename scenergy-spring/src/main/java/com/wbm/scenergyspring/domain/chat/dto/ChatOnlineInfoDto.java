package com.wbm.scenergyspring.domain.chat.dto;

import com.wbm.scenergyspring.domain.chat.entity.ChatOnlineInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
public class ChatOnlineInfoDto implements Serializable {
    private Long id;
    private Long chatRoomId;
    private Long userId;
    private Boolean onlineStatus;

    public static ChatOnlineInfoDto from(ChatOnlineInfo onlineInfo) {
        return ChatOnlineInfoDto.builder()
                .id(onlineInfo.getId())
                .chatRoomId(onlineInfo.getChatRoom().getId())
                .userId(onlineInfo.getChatUser().getUser().getId())
                .onlineStatus(onlineInfo.getOnlineStatus())
                .build();
    }
}