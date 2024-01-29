package com.wbm.scenergyspring.domain.chat.controller.request;

import com.wbm.scenergyspring.domain.chat.service.command.RenameChatRoomCommand;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RenameChatRoomRequest {
    String room_name;
    Long room_id;

    public RenameChatRoomCommand toRenameChatRoomCommand() {
        RenameChatRoomCommand command = RenameChatRoomCommand.builder()
                .roomId(room_id)
                .roomName(room_name)
                .build();
        return command;
    }
}
