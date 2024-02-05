package com.wbm.scenergyspring.domain.chat.controller.response;

import com.wbm.scenergyspring.domain.chat.dto.ListChatRoomDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ListMyChatRoomResponse {
    List<ListChatRoomDto> myChatRoomList;
}
