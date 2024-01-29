package com.wbm.scenergyspring.domain.chat.service.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RenameChatRoomCommand {
    private String roomName;
    private Long roomId;
}
