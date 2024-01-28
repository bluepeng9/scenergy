package com.wbm.scenergyspring.domain.post.jobPost.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wbm.scenergyspring.domain.post.jobPost.entity.JobPost;
import com.wbm.scenergyspring.domain.post.jobPost.repository.JobPostRepository;
import com.wbm.scenergyspring.domain.post.jobPost.service.Command.CreateJobPostCommand;
import com.wbm.scenergyspring.domain.user.entity.User;
import com.wbm.scenergyspring.domain.user.repository.UserRepository;

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
			command.getBookMark()
		);
		return jobPostRepository.save(newJobPost).getId();
	}
}
