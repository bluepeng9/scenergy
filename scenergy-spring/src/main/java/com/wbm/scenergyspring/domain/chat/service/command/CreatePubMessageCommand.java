package com.wbm.scenergyspring.domain.chat.service.command;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CreatePubMessageCommand {
    Long userId;
    Long roomId;
    String messageText;
    String messageType;
    LocalDateTime indexTime;
}
