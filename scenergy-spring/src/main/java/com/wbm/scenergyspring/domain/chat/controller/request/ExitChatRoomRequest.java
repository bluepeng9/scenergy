package com.wbm.scenergyspring.domain.chat.controller.request;

import com.wbm.scenergyspring.domain.chat.service.command.ExitChatRoomCommand;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExitChatRoomRequest {
    Long user_id;
    Long room_id;

    public ExitChatRoomCommand toExitChatRoomCommand() {
        ExitChatRoomCommand command = ExitChatRoomCommand.builder()
                .roomId(room_id)
                .userId(user_id)
                .build();
        return command;
    }
}
