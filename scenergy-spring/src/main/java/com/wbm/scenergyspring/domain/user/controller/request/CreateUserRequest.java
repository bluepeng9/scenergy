package com.wbm.scenergyspring.domain.user.controller.request;

import java.util.List;

import com.wbm.scenergyspring.domain.user.entity.Gender;
import com.wbm.scenergyspring.domain.user.service.command.CreateUserCommand;
import lombok.Data;

@Data
public class CreateUserRequest {
	String email;
	String password;
	Gender gender;
	String username;
	String nickname;

	public CreateUserCommand toCreateUserCommand() {
		return CreateUserCommand.builder()
			.email(email)
			.password(password)
			.gender(gender)
			.username(username)
			.nickname(nickname)
			.build();
	}
}
