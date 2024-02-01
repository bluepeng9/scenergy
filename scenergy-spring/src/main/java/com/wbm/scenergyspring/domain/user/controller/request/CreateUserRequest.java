package com.wbm.scenergyspring.domain.user.controller.request;

import com.wbm.scenergyspring.domain.user.service.command.CreateUserCommand;

import lombok.Data;

@Data
public class CreateUserRequest {
	String email;
	String password;
	String nickname;

	public CreateUserCommand toCreateUserCommand() {
		return CreateUserCommand.builder()
			.email(email)
			.password(password)
			.nickname(nickname)
			.build();
	}
}
