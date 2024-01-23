package com.wbm.scenergyspring.domain.follow.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wbm.scenergyspring.domain.follow.entity.Follow;
import com.wbm.scenergyspring.domain.follow.repository.FollowRepository;
import com.wbm.scenergyspring.domain.follow.service.command.FollowUserCommand;
import com.wbm.scenergyspring.domain.user.entity.User;
import com.wbm.scenergyspring.domain.user.repository.UserRepository;
import com.wbm.scenergyspring.global.exception.EntityAlreadyExistException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FollowService {

	final FollowRepository followRepository;
	final UserRepository userRepository;

	/**
	 *
	 * @param command
	 * @return
	 * @throws com.wbm.scenergyspring.global.exception.EntityAlreadyExistException 이미 팔로우 하는 경우
	 */
	@Transactional
	public Long followUser(FollowUserCommand command) {
		User fromUser = userRepository.getReferenceById(command.getFromUserId());
		User toUser = userRepository.getReferenceById(command.getToUserId());

		boolean alreadyFollow = followRepository.existsByFromAndTo(fromUser, toUser);

		if (alreadyFollow) {
			throw new EntityAlreadyExistException("이미 팔로우 중인 사용자 입니다.");
		}

		Follow follow = Follow.createFollow(
			fromUser,
			toUser
		);

		Follow save = followRepository.save(follow);
		return save.getId();
	}

	/**
	 *
	 * @param command
	 * @return 삭제에 성공했으면 1 아니면 0
	 */
	@Transactional
	public long unFollowUser(FollowUserCommand command) {

		User fromUser = userRepository.getReferenceById(command.getFromUserId());
		User toUser = userRepository.getReferenceById(command.getToUserId());

		long deletedRowCount = followRepository.deleteByFromAndTo(fromUser, toUser);

		return deletedRowCount;
	}
}
