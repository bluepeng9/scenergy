package com.wbm.scenergyspring.domain.chat.controller.request;

import com.wbm.scenergyspring.domain.chat.service.command.GetRoomInfoCommand;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetRoomInfoRequest {
    Long roomId;

    public GetRoomInfoCommand toGetRoomInfoCommand() {
        GetRoomInfoCommand command = GetRoomInfoCommand.builder()
                .RoomId(roomId)
                .build();
        return command;
    }
}




