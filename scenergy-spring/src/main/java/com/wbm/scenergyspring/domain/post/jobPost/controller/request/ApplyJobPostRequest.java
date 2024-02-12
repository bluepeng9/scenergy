package com.wbm.scenergyspring.domain.post.jobPost.controller.request;

import com.wbm.scenergyspring.domain.post.jobPost.service.Command.ApplyJobPostCommand;

import lombok.Data;

@Data
public class ApplyJobPostRequest {
	Long jobPostId;
	Long userId;

	public ApplyJobPostCommand toApplyJobPost() {
		ApplyJobPostCommand command = ApplyJobPostCommand.builder()
			.jobPostId(getJobPostId())
			.userId(getUserId())
			.build();

		return command;
	}
}
