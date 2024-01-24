package com.wbm.scenergyspring.domain.chat.service.command;

import com.wbm.scenergyspring.domain.chat.entity.ChatUser;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CreateChatRoomCommand {
    private String name;
    private int status;
    private List<ChatUser> chatUsers;
}
