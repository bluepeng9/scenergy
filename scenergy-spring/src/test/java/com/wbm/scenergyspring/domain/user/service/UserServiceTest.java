package com.wbm.scenergyspring.domain.user.service;

import com.wbm.scenergyspring.domain.follow.service.FollowService;
import com.wbm.scenergyspring.domain.follow.service.command.FollowUserCommand;
import com.wbm.scenergyspring.domain.user.controller.request.SearchFollowingRequest;
import com.wbm.scenergyspring.domain.user.controller.response.SearchFollowingAllResponse;
import com.wbm.scenergyspring.domain.user.controller.response.SearchFollowingResponse;
import com.wbm.scenergyspring.domain.user.controller.response.SearchUserResponse;
import com.wbm.scenergyspring.domain.user.entity.Gender;
import com.wbm.scenergyspring.domain.user.entity.User;
import com.wbm.scenergyspring.domain.user.repository.UserRepository;
import com.wbm.scenergyspring.domain.user.service.command.CreateUserCommand;
import com.wbm.scenergyspring.domain.user.service.commanresult.FindUserCommandResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class UserServiceTest {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserService userService;

	@Autowired
	FollowService followService;

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
		org.junit.jupiter.api.Assertions.assertTrue(user.isPresent());
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
		org.junit.jupiter.api.Assertions.assertNull(userRepository.findByUsername(username));

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
		org.junit.jupiter.api.Assertions.assertEquals("test", result.getNickname());
	}

	@Test
	@Transactional
	@DisplayName("유저 검색 테스트")
	void searchUserTest() {
		//given
		CreateUserCommand command1 = CreateUserCommand.builder()
				.email("test1@naver.com")
				.password("1234")
				.nickname("차라투스트라")
				.gender(Gender.FEMALE)
				.username("min")
				.build();
		CreateUserCommand command2 = CreateUserCommand.builder()
				.email("test2@naver.com")
				.password("1234")
				.nickname("프리드리히")
				.gender(Gender.FEMALE)
				.username("hyeong")
				.build();
		CreateUserCommand command3 = CreateUserCommand.builder()
				.email("test3@naver.com")
				.password("1234")
				.nickname("데카르트")
				.gender(Gender.FEMALE)
				.username("Lee")
				.build();
		Long userId1 = userService.createUser(command1);
		Long userId2 = userService.createUser(command2);
		Long userId3 = userService.createUser(command3);

		//when
		List<SearchUserResponse> users = userService.searchUser("프리");
		//then
		assertThat(users.size()).isEqualTo(1);
		assertThat(users.get(0).getUserId()).isEqualTo(userId2);
	}

	@Test
	@Transactional
	@DisplayName("팔로잉 유저 전체 검색")
	void searchFollowingAllTest() {
		//given
		CreateUserCommand command1 = CreateUserCommand.builder()
				.email("test1@naver.com")
				.password("1234")
				.nickname("차라투스트라")
				.gender(Gender.FEMALE)
				.username("min")
				.build();
		CreateUserCommand command2 = CreateUserCommand.builder()
				.email("test2@naver.com")
				.password("1234")
				.nickname("프리드리히")
				.gender(Gender.FEMALE)
				.username("hyeong")
				.build();
		CreateUserCommand command3 = CreateUserCommand.builder()
				.email("test3@naver.com")
				.password("1234")
				.nickname("데카르트")
				.gender(Gender.FEMALE)
				.username("Lee")
				.build();
		Long userId1 = userService.createUser(command1);
		Long userId2 = userService.createUser(command2);
		Long userId3 = userService.createUser(command3);

		FollowUserCommand followCommand1 = FollowUserCommand.builder()
				.toUserId(userId1)
				.fromUserId(userId2).build();

		FollowUserCommand followCommand2 = FollowUserCommand.builder()
				.toUserId(userId3)
				.fromUserId(userId2).build();

		followService.followUser(followCommand1);
		followService.followUser(followCommand2);

		//when
		List<SearchFollowingAllResponse> users = userService.searchFollowingAll(userId2);
		//then
		assertThat(users.size()).isEqualTo(2);
		assertThat(users.get(0).getUserId()).isEqualTo(userId1);
		assertThat(users.get(1).getUserId()).isEqualTo(userId3);
	}

	@Test
	@Transactional
	@DisplayName("팔로잉 유저 중 검색")
	void searchFollowingTest() {
		//given
		CreateUserCommand command1 = CreateUserCommand.builder()
				.email("test1@naver.com")
				.password("1234")
				.nickname("차라투스트라")
				.gender(Gender.FEMALE)
				.username("min")
				.build();
		CreateUserCommand command2 = CreateUserCommand.builder()
				.email("test2@naver.com")
				.password("1234")
				.nickname("프리드리히")
				.gender(Gender.FEMALE)
				.username("hyeong")
				.build();
		CreateUserCommand command3 = CreateUserCommand.builder()
				.email("test3@naver.com")
				.password("1234")
				.nickname("데카르트")
				.gender(Gender.FEMALE)
				.username("Lee")
				.build();
		Long userId1 = userService.createUser(command1);
		Long userId2 = userService.createUser(command2);
		Long userId3 = userService.createUser(command3);

		FollowUserCommand followCommand1 = FollowUserCommand.builder()
				.toUserId(userId1)
				.fromUserId(userId2).build();

		FollowUserCommand followCommand2 = FollowUserCommand.builder()
				.toUserId(userId3)
				.fromUserId(userId2).build();

		followService.followUser(followCommand1);
		followService.followUser(followCommand2);

		//when
		SearchFollowingRequest request = SearchFollowingRequest.builder()
				.userId(userId2)
				.word("차라")
				.build();
		List<SearchFollowingResponse> users = userService.searchFollowing(request);
		//then
		assertThat(users.size()).isEqualTo(1);
		assertThat(users.get(0).getUserId()).isEqualTo(userId1);
	}
}