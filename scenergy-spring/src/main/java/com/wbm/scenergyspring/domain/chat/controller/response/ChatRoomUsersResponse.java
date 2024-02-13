package com.wbm.scenergyspring.domain.chat.controller.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChatRoomUsersResponse {

    List<Long> users;
    List<Long> seq;

    public ChatRoomUsersResponse toCreateResponse() {
        ChatRoomUsersResponse response = new ChatRoomUsersResponse();
        List<Long> users = new ArrayList<>();
        List<Long> seq = new ArrayList<>();
        response.users = users;
        response.seq = seq;
        return response;
    }

}
