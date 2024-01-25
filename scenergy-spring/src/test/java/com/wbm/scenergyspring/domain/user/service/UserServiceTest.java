package com.wbm.scenergyspring.domain.user.service;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.wbm.scenergyspring.domain.user.entity.User;
import com.wbm.scenergyspring.domain.user.repository.UserRepository;
import com.wbm.scenergyspring.domain.user.service.command.CreateUserCommand;

@SpringBootTest
@Transactional
class UserServiceTest {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserService userService;

	@Test
	@Transactional
	public void createUser() {
		//given
		CreateUserCommand command = CreateUserCommand.builder()
			.email("test@naver.com")
			.password("1234")
			.nickname("test")
			.build();

		//when
		userService.createUser(
			command
		);
		Optional<User> user = userRepository.findById(1L);

		//then
		Assertions.assertTrue(user.isPresent());
	}
}