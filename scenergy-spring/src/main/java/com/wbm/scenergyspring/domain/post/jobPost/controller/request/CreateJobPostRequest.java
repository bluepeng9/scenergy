package com.wbm.scenergyspring.domain.post.jobPost.controller.request;

import java.time.LocalDateTime;

import com.wbm.scenergyspring.domain.post.jobPost.entity.IsActive;
import com.wbm.scenergyspring.domain.post.jobPost.service.Command.CreateJobPostCommand;

import lombok.Builder;
import lombok.Data;

@Data
public class CreateJobPostRequest {

	Long userId;
	String title;
	String content;
	LocalDateTime expirationDate;
	Long peopleRecruited;
	Long bookMark;
	IsActive isActive;

	public CreateJobPostCommand toCreateJobPost() {
		CreateJobPostCommand command = CreateJobPostCommand.builder()
			.userId(getUserId())
			.title(getTitle())
			.content(getContent())
			.expirationDate(getExpirationDate())
			.peopleRecruited(getPeopleRecruited())
			.bookMark(getBookMark())
			.isActive(getIsActive())
			.build();

		return command;
	}


}
