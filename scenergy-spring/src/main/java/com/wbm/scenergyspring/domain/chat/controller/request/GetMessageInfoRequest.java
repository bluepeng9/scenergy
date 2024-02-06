package com.wbm.scenergyspring.domain.chat.controller.request;

import com.wbm.scenergyspring.domain.chat.service.command.GetMessageInfoCommand;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetMessageInfoRequest {
    Long messageId;

    public GetMessageInfoCommand toGetMessageInfoCommand() {
        GetMessageInfoCommand command = GetMessageInfoCommand.builder()
                .MessageId(messageId)
                .build();
        return command;
    }
}




