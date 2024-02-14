package com.wbm.scenergyspring.domain.videoConferecnce.controller.request;

import lombok.Data;

@Data
public class SavePeerIdRequest {

    Long chatRoomId;
    Long userId;
    String peerId;

}
