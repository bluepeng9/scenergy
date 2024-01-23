package com.wbm.scenergyspring.domain.follow.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.wbm.scenergyspring.domain.follow.entity.Follow;
import com.wbm.scenergyspring.domain.follow.repository.FollowRepository;
import com.wbm.scenergyspring.domain.follow.service.command.FollowUserCommand;
import com.wbm.scenergyspring.domain.user.entity.User;
import com.wbm.scenergyspring.domain.user.repository.UserRepository;
import com.wbm.scenergyspring.global.exception.EntityAlreadyExistException;

@SpringBootTest
@Transactional
class FollowServiceTest {

	@Autowired
	FollowService followService;
	@Autowired
	FollowRepository followRepository;
	@Autowired
	UserRepository userRepository;

	@Test
	@DisplayName("팔로잉 정상 테스트")
	void followUser() {
		//given
		User fromUser = User.createNewUser(
			"test@naver.com",
			"asdf"
		);
		User toUser = User.createNewUser(
			"test2@naver.com",
			"asdf"
		);

		FollowUserCommand command = FollowUserCommand.builder()
			.fromUserId(1L)
			.toUserId(2L)
			.build();

		userRepository.save(toUser);
		userRepository.save(fromUser);

		//when
		Long followId = followService.followUser(command);

		//then
		Follow follow = followRepository.findById(1L).orElseThrow();
		User from = follow.getFrom();
		User to = follow.getTo();

		Assertions.assertThat(followId).isEqualTo(1L);

		Assertions.assertThat(1L).isEqualTo(from.getId());
		Assertions.assertThat(2L).isEqualTo(to.getId());

	}

	@Test
	@DisplayName("이미 팔로잉 하는 경우")
	void followUser2() {
		//given
		User fromUser = User.createNewUser(
			"test@naver.com",
			"asdf"
		);
		User toUser = User.createNewUser(
			"test2@naver.com",
			"asdf"
		);

		Long toUserId = userRepository.save(toUser).getId();
		Long fromUserId = userRepository.save(fromUser).getId();

		FollowUserCommand command = FollowUserCommand.builder()
			.fromUserId(fromUserId)
			.toUserId(toUserId)
			.build();

		followService.followUser(FollowUserCommand.builder()
			.fromUserId(fromUserId)
			.toUserId(toUserId)
			.build());

		//when
		Assertions.assertThatThrownBy(() ->
			followService.followUser(command)
		).isInstanceOf(EntityAlreadyExistException.class);
	}
}