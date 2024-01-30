package com.wbm.scenergyspring.domain.post.jobPost.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wbm.scenergyspring.domain.post.jobPost.controller.response.GetAllJobPostResponse;
import com.wbm.scenergyspring.domain.post.jobPost.entity.JobPost;
import com.wbm.scenergyspring.domain.post.jobPost.repository.JobPostRepository;
import com.wbm.scenergyspring.domain.post.jobPost.service.Command.CreateJobPostCommand;
import com.wbm.scenergyspring.domain.post.jobPost.service.Command.DeleteJobPostCommand;
import com.wbm.scenergyspring.domain.post.jobPost.service.Command.GetAllJobPostCommand;
import com.wbm.scenergyspring.domain.post.jobPost.service.Command.GetJobPostCommand;
import com.wbm.scenergyspring.domain.post.jobPost.service.Command.UpdateJobPostcommand;
import com.wbm.scenergyspring.domain.user.entity.User;
import com.wbm.scenergyspring.domain.user.repository.UserRepository;
import com.wbm.scenergyspring.global.exception.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JobPostService {

	final UserRepository userRepository;
	final JobPostRepository jobPostRepository;

	@Transactional(readOnly = false)
	public Long createJobPost(CreateJobPostCommand command) {
		User postUser = userRepository.getReferenceById(command.getUserId());
		JobPost newJobPost = JobPost.createNewJobPost(
			postUser,
			command.getTitle(),
			command.getContent(),
			command.getExpirationDate(),
			command.getPeopleRecruited(),
			command.getBookMark(),
			command.getIsActive()
		);
		return jobPostRepository.save(newJobPost).getId();
	}

	@Transactional(readOnly = false)
	public Long updateJobPost(UpdateJobPostcommand command) {
		System.out.println("command.getJobPostId() = " + command.getJobPostId());
		JobPost jobPost = jobPostRepository.findById(command.getJobPostId())
			.orElseThrow(() -> new EntityNotFoundException("수정 실패"));
		jobPost.updateJobPost(
			command.getTitle(),
			command.getContent(),
			command.getExpirationDate(),
			command.getPeopleRecruited(),
			command.getBookMark(),
			command.getIsActive()
		);

	return command.getJobPostId();
	}

	@Transactional(readOnly = false)
	public Long deleteJobPost(DeleteJobPostCommand command) {
		jobPostRepository.deleteById(command.getJobPostId());
		return command.getJobPostId();
	}

	public Long getJobPost(GetJobPostCommand command) {
		jobPostRepository.findById(command.getJobPostId())
			.orElseThrow(() -> new EntityNotFoundException("없는 게시글"));
		return command.getJobPostId();
	}

	public List<GetAllJobPostResponse> getAllJobPostList() {
		List<GetAllJobPostResponse> jobPosts = new ArrayList<>();
		for (JobPost jobPost : jobPostRepository.findAll()) {
			User user = jobPost.getUserId();
			GetAllJobPostResponse response = GetAllJobPostResponse.builder()
				.jobPostId(jobPost.getId())
				.userId(user.getId())
				.title(jobPost.getTitle())
				.content(jobPost.getContent())
				.expirationDate(jobPost.getExpirationDate())
				.peopleRecruited(jobPost.getPeopleRecrutied())
				.bookMark(jobPost.getBookMark())
				.isActive(jobPost.getIsActive())
				.build();

			jobPosts.add(response);
		}
		return jobPosts;
	}
}
