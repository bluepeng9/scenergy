package com.wbm.scenergyspring.domain.post.jobPost.controller.request;


import com.wbm.scenergyspring.domain.post.jobPost.service.Command.GetJobPostCommand;

import lombok.Data;

@Data
public class GetJobPostRequest {
	Long jobPostId;

	public GetJobPostCommand toGetJobPost() {
		GetJobPostCommand command = new GetJobPostCommand();
		command.setJobPostId(getJobPostId());
		return command;
	}

}
