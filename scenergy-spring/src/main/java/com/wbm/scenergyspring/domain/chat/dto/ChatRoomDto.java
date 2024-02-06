package com.wbm.scenergyspring.domain.chat.dto;

import com.wbm.scenergyspring.domain.chat.entity.ChatRoom;
import com.wbm.scenergyspring.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ChatRoomDto implements Serializable {
    private Long id;
    private String name;
    private int status;
    private ChatMessageDto recentChatMessage;
    private List<UserDto> users;
    private int unreadMessageCount;

    public static ChatRoomDto from(ChatRoom chatRoom) {
        return ChatRoomDto.builder()
                .id(chatRoom.getId())
                .name(chatRoom.getName())
                .status(chatRoom.getStatus())
                .build();
    }

    public void setRecentChatMessage(ChatMessageDto recentChatMessage) {
        this.recentChatMessage = recentChatMessage;
    }

    public void setChatUsers(List<User> chatUsers) {
        users = new ArrayList<>();
        for (User chatUser : chatUsers) {
            UserDto userDto = new UserDto(
                    chatUser.getId(),
                    chatUser.getNickname(),
                    chatUser.getUsername()
            );
            users.add(userDto);
        }
    }

    @Data
    @AllArgsConstructor
    private class UserDto implements Serializable {
        Long id;
        String nickname;
        String username;
    }
}
