package com.wbm.scenergyspring.domain.user.service;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.wbm.scenergyspring.domain.user.entity.Gender;
import com.wbm.scenergyspring.domain.user.entity.User;
import com.wbm.scenergyspring.domain.user.repository.UserRepository;
import com.wbm.scenergyspring.domain.user.service.command.CreateUserCommand;
import com.wbm.scenergyspring.domain.user.service.commanresult.FindUserCommandResult;

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
			.gender(Gender.FEMALE)
				.username("haeji")
			.build();

		//when
		Long userId = userService.createUser(
			command
		);
		Optional<User> user = userRepository.findById(userId);

		//then
		Assertions.assertTrue(user.isPresent());
	}

	@Test
	public void deleteUser() {

		// given
		CreateUserCommand command = CreateUserCommand.builder()
				.email("test@naver.com")
				.password("1234")
				.nickname("test")
				.gender(Gender.FEMALE)
				.username("haeji")
				.build();
		userService.createUser(command);
		String username = "haeji";
		String password = "1234";

		// when
		userService.deleteUser(password, username);

		// then
		Assertions.assertNull(userRepository.findByUsername(username));

	}

	@Test
	@Transactional
	void findUserReturnsCorrectUser() {
		// given
		CreateUserCommand command = CreateUserCommand.builder()
			.email("test@naver.com")
			.password("1234")
			.nickname("test")
			.gender(Gender.FEMALE)
			.username("haeji")
			.build();
		Long userId = userService.createUser(command);

		// when
		FindUserCommandResult result = userService.findUser(userId);

		// then
		Assertions.assertEquals("test", result.getNickname());
	}
}