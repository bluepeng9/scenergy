package com.wbm.scenergyspring.domain.chat.controller.request;

import com.wbm.scenergyspring.domain.chat.service.command.LoadChatMessageCommand;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoadChatMessageRequest {
    Long room_id;

    public LoadChatMessageCommand toLoadChatMessageCommand() {
        LoadChatMessageCommand command = LoadChatMessageCommand.builder()
                .roomId(room_id)
                .build();
        return command;
    }
}
