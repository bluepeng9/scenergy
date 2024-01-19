package com.wbm.scenergyspring.domain.follow.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.wbm.scenergyspring.domain.follow.entity.Follow;
import com.wbm.scenergyspring.domain.follow.repository.FollowRepository;
import com.wbm.scenergyspring.domain.follow.service.command.FollowUserCommand;
import com.wbm.scenergyspring.domain.user.entity.User;
import com.wbm.scenergyspring.domain.user.repository.UserRepository;

@SpringBootTest
class FollowServiceTest {

	@Autowired
	FollowService followService;
	@Autowired
	FollowRepository followRepository;
	@Autowired
	UserRepository userRepository;

	@Test
	@Transactional
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
}