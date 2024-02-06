package com.wbm.scenergyspring.domain.chat.controller.request;

import com.wbm.scenergyspring.domain.chat.service.command.InviteChatRoomCommand;
import com.wbm.scenergyspring.domain.user.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class InviteChatRoomRequest {
    Long room_id;
    List<User> users;

    public InviteChatRoomCommand toInviteChatRoomCommand() {
        InviteChatRoomCommand command = InviteChatRoomCommand.builder()
                .roomId(room_id)
                .users(users)
                .build();
        return command;
    }
}
