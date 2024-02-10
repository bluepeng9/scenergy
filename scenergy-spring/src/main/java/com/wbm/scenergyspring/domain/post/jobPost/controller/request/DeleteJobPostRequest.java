package com.wbm.scenergyspring.domain.post.jobPost.controller.request;

import com.wbm.scenergyspring.domain.post.jobPost.service.Command.CreateJobPostCommand;
import com.wbm.scenergyspring.domain.post.jobPost.service.Command.DeleteJobPostCommand;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeleteJobPostRequest {
	Long jobPostId;

	public DeleteJobPostCommand toDeleteJobPost() {
		DeleteJobPostCommand command = new DeleteJobPostCommand();
		command.setJobPostId(getJobPostId());
		return command;
	}
}
