package com.wbm.scenergyspring.domain.user.service;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wbm.scenergyspring.domain.user.entity.User;
import com.wbm.scenergyspring.domain.user.repository.UserRepository;
import com.wbm.scenergyspring.domain.user.service.command.CreateUserCommand;

import lombok.RequiredArgsConstructor;

@SpringBootTest
@RequiredArgsConstructor
class UserServiceTest {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserService userService;

	@Test
	public void createUser() {
		//given
		CreateUserCommand command = new CreateUserCommand(
		);
		command.setEmail("test@gmail.com");
		command.setPassword("asdf");

		//when
		userService.createUser(
			command
		);
		Optional<User> user = userRepository.findById(1L);

		//then
		Assertions.assertTrue(user.isPresent());
	}
}