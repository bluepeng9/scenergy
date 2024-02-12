package com.wbm.scenergyspring.domain.post.jobPost.controller.request;

import com.wbm.scenergyspring.domain.post.jobPost.service.Command.BookMarkCommand;

import lombok.Data;

@Data
public class JobPostBookMarkRequest {
	Long jobPostId;
	Long userId;

	public BookMarkCommand bookMarkJobPost() {
		BookMarkCommand command = BookMarkCommand.builder()
			.jobPostId(getJobPostId())
			.userId(getUserId())
			.build();
		return command;
	}
}
