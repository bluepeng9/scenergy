package com.wbm.scenergyspring.domain.chat.controller.request;

import lombok.Data;

@Data
public class GetToConnectUserRequest {

    Long chatRoomId;
    Long userId;

}
