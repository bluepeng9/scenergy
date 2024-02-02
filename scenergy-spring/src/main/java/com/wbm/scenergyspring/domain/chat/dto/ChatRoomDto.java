package com.wbm.scenergyspring.domain.chat.dto;

import com.wbm.scenergyspring.domain.chat.entity.ChatRoom;
import com.wbm.scenergyspring.domain.user.entity.User;
import com.wbm.scenergyspring.global.entity.BaseEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ChatRoomDto extends BaseEntity {
    private Long id;
    private String name;
    private int status;
    private ChatMessageDto firstChatMessage;
    private List<User> chatUsers;

    public static ChatRoomDto from(ChatRoom chatRoom) {
        return ChatRoomDto.builder()
                .id(chatRoom.getId())
                .name(chatRoom.getName())
                .status(chatRoom.getStatus())
                .build();
    }

    public void setFirstChatMessage(ChatMessageDto firstChatMessage) {
        this.firstChatMessage = firstChatMessage;
    }
}
