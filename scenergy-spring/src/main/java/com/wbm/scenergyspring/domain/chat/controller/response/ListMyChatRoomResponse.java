package com.wbm.scenergyspring.domain.chat.controller.response;

import com.wbm.scenergyspring.domain.chat.entity.ChatRoom;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ListMyChatRoomResponse {
    List<ChatRoom> MyChatRoomList;
}
