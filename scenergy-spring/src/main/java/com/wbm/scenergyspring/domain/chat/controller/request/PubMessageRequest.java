package com.wbm.scenergyspring.domain.chat.controller.request;

import com.wbm.scenergyspring.domain.chat.service.command.CreatePubMessageCommand;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PubMessageRequest {
    Long user_id;
    Long room_id;
    String message;

    public CreatePubMessageCommand toCreatePubMessageCommand() {
        CreatePubMessageCommand command = CreatePubMessageCommand.builder()
                .userId(user_id)
                .roomId(room_id)
                .messageText(message)
                .build();
        return command;
    }
}
