package com.wbm.scenergyspring.domain.chat.service.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetRoomInfoCommand {
    private Long RoomId;
}
