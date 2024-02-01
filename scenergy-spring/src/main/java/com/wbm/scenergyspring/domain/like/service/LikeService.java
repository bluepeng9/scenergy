package com.wbm.scenergyspring.domain.like.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wbm.scenergyspring.domain.like.entity.Like;
import com.wbm.scenergyspring.domain.like.repository.LikeRepository;
import com.wbm.scenergyspring.domain.like.service.command.LikePostCommand;
import com.wbm.scenergyspring.domain.like.service.command.UnlikePostCommand;
import com.wbm.scenergyspring.domain.post.videoPost.entity.VideoPost;
import com.wbm.scenergyspring.domain.post.videoPost.repository.VideoPostRepository;
import com.wbm.scenergyspring.domain.user.entity.User;
import com.wbm.scenergyspring.domain.user.repository.UserRepository;
import com.wbm.scenergyspring.global.exception.EntityAlreadyExistException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeService {

	final LikeRepository likeRepository;
	final VideoPostRepository videoPostRepository;
	final UserRepository userRepository;

	/**
	 * 게시물에 좋아요 누르기
	 * @param command
	 */
	@Transactional(readOnly = false)
	public void likePost(LikePostCommand command) {
		VideoPost videoPost = videoPostRepository.getReferenceById(command.getPostId());
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

	@Transactional(readOnly = false)
	public void unlikePost(UnlikePostCommand command) {
		VideoPost videoPost = videoPostRepository.getReferenceById(command.getPostId());
		User user = userRepository.getReferenceById(command.getUserId());

		// 좋아요를 누른 적이 없는 게시물인지 확인
		if (likeRepository.findByVideoPostAndUser(videoPost, user).isEmpty()) {
			throw new EntityAlreadyExistException("좋아요를 누른 적이 없는 게시물입니다.");
		}

		Like like = likeRepository.findByVideoPostAndUser(videoPost, user).orElseThrow(
			() -> new EntityAlreadyExistException("좋아요를 누른 적이 없는 게시물입니다.")
		);

		likeRepository.delete(like);
	}

	/**
	 * 게시물에 좋아요를 누른 유저 목록 조회
	 *
	 * @param postId
	 * @return
	 * @TODO 페이징 적용
	 */
	@Transactional(readOnly = true)
	public List<Like> findLikeListByPostId(Long postId) {
		VideoPost videoPost = videoPostRepository.getReferenceById(postId);
		List<Like> likeList = likeRepository.findByVideoPost(videoPost);
		return likeList;
	}

	/**
	 * 게시물에 좋아요를 누른 유저 수 조회
	 * @param postId
	 * @return
	 */
	@Transactional(readOnly = true)
	public int countLikeByPostId(Long postId) {
		VideoPost videoPost = videoPostRepository.getReferenceById(postId);
		return likeRepository.countByVideoPost(videoPost);
	}

}
