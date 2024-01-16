package com.wbm.scenergyspring.user.controller.request;

import com.wbm.scenergyspring.user.service.command.CreateUserCommand;
import lombok.Data;

@Data
public class CreateUserRequest {
    String email;
    String password;

    public CreateUserCommand toCreateUserCommand() {
        CreateUserCommand command = new CreateUserCommand();
        command.setEmail(command.getEmail());
        command.setEmail(command.getPassword());
        return command;
    }
}
