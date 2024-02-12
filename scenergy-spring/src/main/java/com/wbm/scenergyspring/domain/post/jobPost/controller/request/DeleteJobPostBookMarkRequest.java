package com.wbm.scenergyspring.domain.post.jobPost.controller.request;

import com.wbm.scenergyspring.domain.post.jobPost.service.Command.DeleteBookMarkCommand;

import lombok.Data;

@Data
public class DeleteJobPostBookMarkRequest {
	Long jobPostId;
	Long userId;

	public DeleteBookMarkCommand deleteBookmark() {
		DeleteBookMarkCommand command = DeleteBookMarkCommand.builder()
			.jobPostId(getJobPostId())
			.userId(getUserId())
			.build();
		return command;
	}
}
