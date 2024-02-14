package com.wbm.scenergyspring.domain.follow.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wbm.scenergyspring.domain.follow.entity.Follow;
import com.wbm.scenergyspring.domain.follow.repository.FollowRepository;
import com.wbm.scenergyspring.domain.follow.service.command.FindAllFollowersCommand;
import com.wbm.scenergyspring.domain.follow.service.command.FindAllFollowingCommand;
import com.wbm.scenergyspring.domain.follow.service.command.FollowUserCommand;
import com.wbm.scenergyspring.domain.follow.service.command.UnFollowUserCommand;
import com.wbm.scenergyspring.domain.follow.service.commandresult.FollowCommandResult;
import com.wbm.scenergyspring.domain.user.entity.User;
import com.wbm.scenergyspring.domain.user.repository.UserRepository;
import com.wbm.scenergyspring.global.exception.EntityAlreadyExistException;
import com.wbm.scenergyspring.global.exception.EntityNotFoundException;

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
	public FollowCommandResult followUser(FollowUserCommand command) {
		User fromUser = userRepository.getReferenceById(command.getFromUserId());
		User toUser = userRepository.getReferenceById(command.getToUserId());

		if (fromUser.getId() == toUser.getId()) {
			throw new EntityAlreadyExistException("자기 자신을 팔로우 할 수 없습니다.");
		}

		boolean alreadyFollow = followRepository.existsByFromAndTo(fromUser, toUser);

		if (alreadyFollow) {
			throw new EntityAlreadyExistException("이미 팔로우 중인 사용자 입니다.");
		}

		Follow follow = Follow.createFollow(
			fromUser,
			toUser
		);

		Follow save = followRepository.save(follow);

		Long countFollow = followRepository.countByTo(toUser);

		return FollowCommandResult.builder()
			.followId(save.getId())
			.fromUserId(save.getFrom().getId())
			.toUserId(save.getTo().getId())
			.userFollowerCount(countFollow)
			.build();
	}

	/**
	 * 언팔로우
	 *
	 * @param command
	 * @return
	 */
	@Transactional
	public Long unFollowUser(UnFollowUserCommand command) {
		User fromUserId = userRepository.getReferenceById(command.getFromUserId());
		User toUserId = userRepository.getReferenceById(command.getToUserId());
		Follow follow = followRepository.findByFromAndTo(fromUserId, toUserId)
			.orElseThrow(() -> new EntityNotFoundException("팔로우 정보가 없습니다."));

		if (!follow.getFrom().getId().equals(command.getFromUserId())) {
			throw new EntityNotFoundException("팔로우 정보가 없습니다.");
		}

		followRepository.delete(follow);
		return followRepository.countByTo(follow.getTo());
	}

	/**
	 * 팔로워 찾기
	 * @param command
	 * @return
	 */
	public List<Follow> findAllFollowers(FindAllFollowersCommand command) {
		User toUser = userRepository.getReferenceById(command.getUserId());
		return followRepository.findAllByTo(toUser);
	}

	/**
	 * 팔로잉 하는 사람 찾기
	 * @param command
	 * @return
	 */
	public List<Follow> findAllFollowing(FindAllFollowingCommand command) {
		User userId = userRepository.getReferenceById(command.getUserId());
		return followRepository.findAllByFrom(userId);
	}

	public Optional<Follow> getFollow(Long fromUserId, Long toUserId) {
		User toUser = userRepository.getReferenceById(toUserId);
		User fromUser = userRepository.getReferenceById(fromUserId);
		return followRepository.findByFromAndTo(fromUser, toUser);
	}
}
