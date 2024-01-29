package com.wbm.scenergyspring.domain.chat.service.command;

import com.wbm.scenergyspring.domain.user.entity.User;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class InviteChatRoomCommand {
    Long roomId;
    List<User> users;
}
