package com.wbm.scenergyspring.domain.chat.dto;

import com.wbm.scenergyspring.domain.chat.entity.ChatOnlineInfo;
import com.wbm.scenergyspring.domain.chat.entity.ChatRoom;
import com.wbm.scenergyspring.domain.user.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatOnlineInfoDto {
    private Long id;
    private ChatRoom chatRoom;
    private User user;
    private Boolean onlineStatus;

    public static ChatOnlineInfoDto from(ChatOnlineInfo onlineInfo) {
        return ChatOnlineInfoDto.builder()
                .id(onlineInfo.getId())
                .chatRoom(onlineInfo.getChatRoom())
                .user(onlineInfo.getUser())
                .onlineStatus(onlineInfo.getOnlineStatus())
                .build();
    }
}