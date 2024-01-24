package com.wbm.scenergyspring.domain.like.service;

import org.springframework.stereotype.Service;

import com.wbm.scenergyspring.domain.like.Like;
import com.wbm.scenergyspring.domain.like.repository.LikeRepository;
import com.wbm.scenergyspring.domain.like.service.command.LikePostCommand;
import com.wbm.scenergyspring.domain.post.entity.VideoPost;
import com.wbm.scenergyspring.domain.post.repository.PostRepository;
import com.wbm.scenergyspring.domain.user.entity.User;
import com.wbm.scenergyspring.domain.user.repository.UserRepository;
import com.wbm.scenergyspring.global.exception.EntityAlreadyExistException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LikeService {

	final LikeRepository likeRepository;
	final PostRepository postRepository;
	final UserRepository userRepository;

	public void likePost(LikePostCommand command) {
		VideoPost videoPost = postRepository.getReferenceById(command.getPostId());
		User user = userRepository.getReferenceById(command.getUserId());

		// 이미 좋아요를 눌렀는지 확인
		if (likeRepository.findByVideoPostAndUser(videoPost, user).isPresent()) {
			throw new EntityAlreadyExistException("이미 좋아요를 누른 게시물입니다.");
		}

		Like like = Like.createNewLike(
			videoPost,
			user
		);
		likeRepository.save(like);

	}

}
