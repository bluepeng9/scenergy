package com.wbm.scenergyspring.domain.chat.controller.response;

import com.wbm.scenergyspring.domain.chat.dto.ChatRoomDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ListMyChatRoomResponse {
    List<ChatRoomDto> myChatRoomList;
}
