package com.wbm.scenergyspring.domain.follow.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wbm.scenergyspring.domain.follow.entity.Follow;
import com.wbm.scenergyspring.domain.follow.repository.FollowRepository;
import com.wbm.scenergyspring.domain.follow.service.command.FollowUserCommand;
import com.wbm.scenergyspring.domain.user.entity.User;
import com.wbm.scenergyspring.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FollowService {

	final FollowRepository followRepository;
	final UserRepository userRepository;

	@Transactional
	public Long followUser(FollowUserCommand command) {
		User fromUser = userRepository.getReferenceById(command.getFromUserId());
		User toUser = userRepository.getReferenceById(command.getToUserId());

		Follow follow = Follow.createFollow(
			fromUser,
			toUser
		);
		Follow save = followRepository.save(follow);
		return save.getId();
	}
}
