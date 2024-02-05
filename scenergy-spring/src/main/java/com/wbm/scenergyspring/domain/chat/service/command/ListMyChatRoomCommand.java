package com.wbm.scenergyspring.domain.chat.service.command;

import lombok.Builder;
import lombok.Data;

/**
 * userId: 요청한 user id
 */
@Data
@Builder
public class ListMyChatRoomCommand {
    Long userId;
}
