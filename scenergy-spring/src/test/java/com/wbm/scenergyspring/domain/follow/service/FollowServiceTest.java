package com.wbm.scenergyspring.domain.follow.service;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.wbm.scenergyspring.domain.follow.entity.Follow;
import com.wbm.scenergyspring.domain.follow.repository.FollowRepository;
import com.wbm.scenergyspring.domain.follow.service.command.FindAllFollowersCommand;
import com.wbm.scenergyspring.domain.follow.service.command.FindAllFollowingCommand;
import com.wbm.scenergyspring.domain.follow.service.command.FollowUserCommand;
import com.wbm.scenergyspring.domain.user.entity.User;
import com.wbm.scenergyspring.domain.user.repository.UserRepository;
import com.wbm.scenergyspring.global.exception.EntityAlreadyExistException;
import com.wbm.scenergyspring.util.UserGenerator;

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
		User fromUser = UserGenerator.createRandomMember();
		User toUser = UserGenerator.createRandomMember();

		Long toUserId = userRepository.save(toUser).getId();
		Long fromUserId = userRepository.save(fromUser).getId();
		FollowUserCommand command = FollowUserCommand.builder()
			.fromUserId(fromUserId)
			.toUserId(toUserId)
			.build();

		//when
		Long followId = followService.followUser(command);

		//then
		Follow follow = followRepository.findById(followId).orElseThrow();
		User from = follow.getFrom();
		User to = follow.getTo();

		Assertions.assertThat(fromUserId).isEqualTo(from.getId());
		Assertions.assertThat(toUserId).isEqualTo(to.getId());

	}

	@Test
	@DisplayName("이미 팔로잉 하는 경우")
	void followUser2() {
		//given
		User fromUser = UserGenerator.createRandomMember();
		User toUser = UserGenerator.createRandomMember();

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

	@Test
	@DisplayName("팔로잉 삭제")
	void unFollowUser() {
		//given
		User fromUser = UserGenerator.createRandomMember();
		User toUser = UserGenerator.createRandomMember();

		Long toUserId = userRepository.save(toUser).getId();
		Long fromUserId = userRepository.save(fromUser).getId();

		followService.followUser(FollowUserCommand.builder()
			.fromUserId(fromUserId)
			.toUserId(toUserId)
			.build());

		//when
		followRepository.deleteByFromAndTo(fromUser, toUser);

		//then
		long count = followRepository.count();
		Assertions.assertThat(count).isEqualTo(0);
	}

	@Test
	@DisplayName("모든 팔로워 찾기")
	void findAllFollowers() {
		//given
		User parent = UserGenerator.createRandomMember();
		User child1 = UserGenerator.createRandomMember();
		User child2 = UserGenerator.createRandomMember();

		Long toUserId = userRepository.save(parent).getId();
		Long child1Id = userRepository.save(child1).getId();
		Long child2Id = userRepository.save(child2).getId();

		//when
		FollowUserCommand command = FollowUserCommand.builder()
			.fromUserId(child1Id)
			.toUserId(toUserId)
			.build();
		FollowUserCommand command2 = FollowUserCommand.builder()
			.fromUserId(child2Id)
			.toUserId(toUserId)
			.build();

		followService.followUser(command);
		followService.followUser(command2);

		List<Follow> allFollowers = followService.findAllFollowers(
			FindAllFollowersCommand.builder()
				.toUserId(toUserId)
				.build()
		);

		//then
		Assertions.assertThat(allFollowers).hasSize(2);
	}

	@Test
	@DisplayName("모든 팔로잉 찾기")
	void findAllFollowing() {

		//given
		User parent = UserGenerator.createRandomMember();
		User parent2 = UserGenerator.createRandomMember();
		User child = UserGenerator.createRandomMember();

		Long parentId = userRepository.save(parent).getId();
		Long parentId2 = userRepository.save(parent2).getId();
		Long childId = userRepository.save(child).getId();

		FollowUserCommand command = FollowUserCommand.builder()
			.fromUserId(childId)
			.toUserId(parentId)
			.build();
		FollowUserCommand command2 = FollowUserCommand.builder()
			.fromUserId(childId)
			.toUserId(parentId2)
			.build();

		followService.followUser(command);
		followService.followUser(command2);

		//when
		List<Follow> allFollowing = followService.findAllFollowing(
			FindAllFollowingCommand.builder()
				.fromUserId(childId)
				.build()
		);

		//then
		Assertions.assertThat(allFollowing).hasSize(2);

	}
}