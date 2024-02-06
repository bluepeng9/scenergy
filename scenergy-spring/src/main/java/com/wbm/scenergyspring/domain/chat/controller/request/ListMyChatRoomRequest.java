package com.wbm.scenergyspring.domain.chat.controller.request;

import com.wbm.scenergyspring.domain.chat.service.command.ListMyChatRoomCommand;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ListMyChatRoomRequest {
    Long user_id;

    public ListMyChatRoomCommand toListMyChatRoomCommand() {
        ListMyChatRoomCommand command = ListMyChatRoomCommand.builder()
                .userId(user_id)
                .build();
        return command;
    }
}
