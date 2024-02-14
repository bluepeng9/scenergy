package com.wbm.scenergyspring.domain.videoConferecnce.service.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SavePeerIdCommand {

    Long chatRoomId;
    Long userId;
    String peerId;

}
