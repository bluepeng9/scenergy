package com.wbm.scenergyspring.domain.chat.controller.response;

import com.wbm.scenergyspring.domain.chat.dto.ChatRoomDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetRoomInfoResponse {
    ChatRoomDto roomInfo;
}

