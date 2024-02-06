package com.wbm.scenergyspring.domain.chat.controller.request;

import com.wbm.scenergyspring.domain.chat.service.command.CreateChatRoomCommand;
import com.wbm.scenergyspring.domain.user.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CreateChatRoomRequest {
    String chatroom_name;
    int status;
    List<User> users;

    public CreateChatRoomCommand toCreateChatRoomCommand() {
        CreateChatRoomCommand command = CreateChatRoomCommand.builder()
                .roomName(chatroom_name)
                .status(status)
                .users(users)
                .build();
        return command;
    }
}
