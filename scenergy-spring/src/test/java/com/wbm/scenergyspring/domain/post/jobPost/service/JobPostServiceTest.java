package com.wbm.scenergyspring.domain.post.jobPost.service;


import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.wbm.scenergyspring.domain.post.jobPost.controller.response.GetJobPostCommandResponse;
import com.wbm.scenergyspring.domain.post.jobPost.entity.IsActive;
import com.wbm.scenergyspring.domain.post.jobPost.entity.JobPost;
import com.wbm.scenergyspring.domain.post.jobPost.repository.JobPostRepository;
import com.wbm.scenergyspring.domain.post.jobPost.service.Command.CreateJobPostCommand;
import com.wbm.scenergyspring.domain.post.jobPost.service.Command.DeleteJobPostCommand;
import com.wbm.scenergyspring.domain.post.jobPost.service.Command.GetJobPostCommand;
import com.wbm.scenergyspring.domain.user.entity.User;
import com.wbm.scenergyspring.domain.user.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@SpringBootTest
@Transactional
class JobPostServiceTest {

	@Autowired
	JobPostService jobPostService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	JobPostRepository jobPostRepository;

	@Test
	@DisplayName("게시글 추가")
	void createJobPost() {
		// given
		User user = User.createNewUser(
			"aaa@naver.com",
			"aaaaa",
			"aaaaa"
		);
		User saveUser = userRepository.save(user);

		JobPost newJobPost = JobPost.createNewJobPost(
			user,
			"aaa",
			"aaaa",
			LocalDateTime.parse("2024-02-01T10:00:00"),
			4L,
			0L,
			IsActive.active
		);

		CreateJobPostCommand command = CreateJobPostCommand.builder()
			.userId(saveUser.getId())
			.title(newJobPost.getTitle())
			.content(newJobPost.getContent())
			.expirationDate(newJobPost.getExpirationDate())
			.peopleRecruited(newJobPost.getPeopleRecruited())
			.bookMark(newJobPost.getBookMark())
			.isActive(newJobPost.getIsActive())
			.build();

		// when
		Long id = jobPostService.createJobPost(command);
		System.out.println(id);

		// then
		JobPost findJobPost = jobPostRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("없는 게시글"));

		Assertions.assertThat(id).isEqualTo(findJobPost.getId());
	}

	// @Test
	// @DisplayName("게시글 수정")
	// void updateJobPost() {
	// 	// given
	// 	User user = User.createNewUser(
	// 		"aaa@naver.com",
	// 		"aaaaa",
	// 		"aaaaa"
	// 	);
	//
	// 	User saveUser = userRepository.save(user);
	// 	JobPost newJobPost = JobPost.createNewJobPost(
	// 		user,
	// 		"aaa",
	// 		"aaaa",
	// 		LocalDateTime.parse("2024-02-01T10:00:00"),
	// 		4L,
	// 		0L,
	// 		IsActive.active
	// 	);
	//
	// 	jobPostRepository.save(newJobPost);
	//
	// 	// when
	// 	UpdateJobPostcommand command = UpdateJobPostcommand.builder()
	// 		.jobPostId(newJobPost.getId())
	// 		.title("bbb")
	// 		.content("bbb")
	// 		.expirationDate(LocalDateTime.parse("2024-02-01T10:00:00"))
	// 		.peopleRecruited(10L)
	// 		.bookMark(1L)
	// 		.isActive(IsActive.inactive)
	// 		.build();
	//
	// 	jobPostService.updateJobPost(command);
	//
	// 	// then
	// 	Assertions.assertThat(newJobPost.getTitle()).isEqualTo(command.getTitle());


	@Test
	@DisplayName("게시글 삭제")
	void deleteJobPost() {

		long now = jobPostRepository.count();

		// given
		User user = User.createNewUser(
			"aaa@naver.com",
			"aaaaa",
			"aaaa"
		);
		JobPost newJobPost = JobPost.createNewJobPost(
			user,
			"aaa",
			"aaaa",
			LocalDateTime.parse("2024-02-01T10:00:00"),
			4L,
			0L,
			IsActive.active
		);

		jobPostRepository.save(newJobPost);

		// when
		DeleteJobPostCommand command = new DeleteJobPostCommand();
		command.setJobPostId(newJobPost.getId());
		jobPostService.deleteJobPost(command);

		// then
		Assertions.assertThat(now).isEqualTo(jobPostRepository.count());
	}

	@Test
	@DisplayName("상세 게시글 조회")
	void getJobPost() {
		// given
		GetJobPostCommand command = new GetJobPostCommand();
		command.setJobPostId(3L);
		GetJobPostCommandResponse response = jobPostService.getJobPost(command);

		// when
		JobPost findJob = jobPostRepository.findById(3L)
			.orElseThrow(() -> new EntityNotFoundException("없는 게시글"));

		// then
		Assertions.assertThat(response.getTitle()).isEqualTo(findJob.getTitle());
	}

	@Test
	@DisplayName("전체 게시글 조회")
	void getAllJobPostList() {
		Assertions.assertThat(jobPostService.getAllJobPostList().size()).isEqualTo(jobPostRepository.count());
	}
}