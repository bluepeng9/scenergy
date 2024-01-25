package com.wbm.scenergyspring.domain.user.controller.request;

import com.wbm.scenergyspring.domain.user.entity.Gender;
import com.wbm.scenergyspring.domain.user.service.command.CreateUserCommand;

import lombok.Data;

@Data
public class CreateUserRequest {
    String email;
    String password;
	Gender gender;
	String username;

    public CreateUserCommand toCreateUserCommand() {
        CreateUserCommand command = new CreateUserCommand();
		command.setEmail(getEmail());
		command.setPassword(getPassword());
		command.setGender(getGender());
		command.setUsername(getUsername());
        return command;
    }
}
