package com.wbm.scenergyspring.domain.like.service;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.wbm.scenergyspring.domain.like.entity.Like;
import com.wbm.scenergyspring.domain.like.repository.LikeRepository;
import com.wbm.scenergyspring.domain.like.service.command.LikePostCommand;
import com.wbm.scenergyspring.domain.like.service.command.UnlikePostCommand;
import com.wbm.scenergyspring.domain.post.videoPost.entity.Video;
import com.wbm.scenergyspring.domain.post.videoPost.repository.VideoPostRepository;
import com.wbm.scenergyspring.domain.post.videoPost.repository.VideoRepository;
import com.wbm.scenergyspring.domain.post.videoPost.service.VideoPostService;
import com.wbm.scenergyspring.domain.post.videoPost.service.command.CreateVideoCommand;
import com.wbm.scenergyspring.domain.post.videoPost.service.command.VideoPostCommand;
import com.wbm.scenergyspring.domain.user.repository.UserRepository;
import com.wbm.scenergyspring.domain.user.service.UserService;
import com.wbm.scenergyspring.domain.user.service.command.CreateUserCommand;
import com.wbm.scenergyspring.util.RandomValueGenerator;
import com.wbm.scenergyspring.util.UserGenerator;

@SpringBootTest
class LikeServiceTest {

	@Autowired
	UserRepository userRepository;
	@Autowired
	LikeService likeService;
	@Autowired
	VideoPostService videoPostService;
	@Autowired
	VideoPostRepository videoPostRepository;
	@Autowired
	UserService userService;
	@Autowired
	VideoRepository videoRepository;
	@Autowired
	LikeRepository likeRepository;

	Long saveUser() {
		return userService.createUser(
			CreateUserCommand.builder()
				.email(RandomValueGenerator.generateRandomEmail())
				.password("1234")
				.nickname(RandomValueGenerator.generateRandomString(10, 20))
				.build());
	}

	Long saveVideo() {
		Video video = videoPostService.createVideo(
			CreateVideoCommand.builder()
				.videoUrlPath(RandomValueGenerator.generateRandomUrl())
				.thumbnailUrlPath(RandomValueGenerator.generateRandomUrl())
				.videoTitle(RandomValueGenerator.generateRandomString(10, 20))
				.artist(RandomValueGenerator.generateRandomString(10, 20))
				.build());
		return video.getId();
	}

	Long saveVideoPost(Long userId, Long videoId) {
		Video video = videoRepository.getReferenceById(videoId);
		return videoPostService.createVideoPost(VideoPostCommand.builder()
			.userId(userId)
			.title(RandomValueGenerator.generateRandomString(10, 20))
			.content(RandomValueGenerator.generateRandomString(10, 20))
			.video(video)
			.genreTagIds(new ArrayList<>())
			.instrumentTagIds(new ArrayList<>())
			.build()).getId();
	}

	@Test
	@Transactional
	void likePost() {
		//given
		Long savedUser = saveUser();
		Long savedVideo = saveVideo();
		Long savedVideoPost = saveVideoPost(savedUser, savedVideo);

		//when
		LikePostCommand command = LikePostCommand.builder()
			.userId(savedUser)
			.postId(savedVideoPost)
			.build();
		likeService.likePost(command);

		//then
		// 1. 좋아요를 누른 게시물인지 확인
		Assertions.assertThat(likeRepository.existsByUserAndVideoPost(userRepository.getReferenceById(savedUser),
			videoPostRepository.getReferenceById(savedVideoPost))).isTrue();

		// 2. 좋아요를 누른 게시물의 좋아요 수가 1인지 확인
		Assertions.assertThat(likeRepository.countByVideoPost(videoPostRepository.getReferenceById(savedVideoPost)))
			.isEqualTo(1);

	}

	@Test
	@Transactional
	void unlikePost() {
		//given
		Long savedUser = saveUser();
		Long savedVideo = saveVideo();
		Long savedVideoPost = saveVideoPost(savedUser, savedVideo);

		LikePostCommand command = LikePostCommand.builder()
			.userId(savedUser)
			.postId(savedVideoPost)
			.build();

		likeService.likePost(command);

		//when
		likeService.unlikePost(UnlikePostCommand.builder()
			.userId(savedUser)
			.postId(savedVideoPost)
			.build());

		//then

		// 1. 좋아요를 누른 게시물이 없는지 확인
		Assertions.assertThat(
			likeRepository.existsByUserAndVideoPost(userRepository.getReferenceById(savedUser),
				videoPostRepository.getReferenceById(savedVideoPost))
		).isFalse();

		// 2. 좋아요를 누른 게시물의 좋아요 수가 0인지 확인
		Assertions.assertThat(likeRepository.countByVideoPost(videoPostRepository.getReferenceById(savedVideoPost)))
			.isEqualTo(0);

	}

	@Test
	@Transactional
	void findLikeListByPostId() {

		//given

		Long savedUser = saveUser();
		Long savedUser2 = saveUser();

		Long savedVideo = saveVideo();
		Long savedVideoPost = saveVideoPost(savedUser, savedVideo);

		LikePostCommand command = LikePostCommand.builder()
			.userId(savedUser)
			.postId(savedVideoPost)
			.build();

		LikePostCommand command2 = LikePostCommand.builder()
			.userId(savedUser2)
			.postId(savedVideoPost)
			.build();

		likeService.likePost(command);
		likeService.likePost(command2);

		//when
		List<Like> likeList = likeService.findLikeListByPostId(savedVideoPost);

		//then
		Assertions.assertThat(likeList.size()).isEqualTo(2);

	}

	@Test
	@Transactional
	void countLikeByPostId() {
		//given
		Long savedUser = saveUser();
		Long savedUser2 = saveUser();

		Long savedVideo = saveVideo();
		Long savedVideoPost = saveVideoPost(savedUser, savedVideo);

		LikePostCommand command = LikePostCommand.builder()
			.userId(savedUser)
			.postId(savedVideoPost)
			.build();

		LikePostCommand command2 = LikePostCommand.builder()
			.userId(savedUser2)
			.postId(savedVideoPost)
			.build();

		likeService.likePost(command);
		likeService.likePost(command2);

		//when
		int count = likeService.countLikeByPostId(savedVideoPost);

		//then
		Assertions.assertThat(count).isEqualTo(2);
	}
}

