package com.wbm.scenergyspring.domain.chat.service.command;

import com.wbm.scenergyspring.domain.user.entity.User;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CreateChatRoomCommand {
    private String roomName;
    private int status; //0: 1대1 채팅방, 1: 그룹 채팅방
    private List<User> users;
}
