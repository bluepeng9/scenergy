package com.wbm.scenergyspring.domain.chat.controller.request;

import com.wbm.scenergyspring.domain.chat.service.command.LoadChatMessageCommand;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoadChatMessageRequest {
    Long chatMessage_id;

    public LoadChatMessageCommand toLoadChatMessageCommand() {
        LoadChatMessageCommand command = LoadChatMessageCommand.builder()
                .chatMessageId(chatMessage_id)
                .build();
        return command;
    }
}
