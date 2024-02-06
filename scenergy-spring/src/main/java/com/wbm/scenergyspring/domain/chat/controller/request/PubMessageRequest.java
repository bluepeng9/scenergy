package com.wbm.scenergyspring.domain.chat.controller.request;

import com.wbm.scenergyspring.domain.chat.service.command.CreatePubMessageCommand;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PubMessageRequest {
    Long user_id;
    Long room_id;
    String message;
    String messageType;

    public CreatePubMessageCommand toCreatePubMessageCommand() {
        CreatePubMessageCommand command = CreatePubMessageCommand.builder()
                .userId(user_id)
                .roomId(room_id)
                .messageText(message)
                .messageType(messageType)
                .build();
        return command;
    }
}
