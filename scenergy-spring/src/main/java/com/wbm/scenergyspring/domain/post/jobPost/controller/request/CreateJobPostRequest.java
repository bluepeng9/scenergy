package com.wbm.scenergyspring.domain.post.jobPost.controller.request;

import java.time.LocalDateTime;

import com.wbm.scenergyspring.domain.post.jobPost.entity.IsActive;
import com.wbm.scenergyspring.domain.post.jobPost.service.Command.CreateJobPostCommand;

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
		CreateJobPostCommand command = new CreateJobPostCommand();
		command.setUserId(getUserId());
		command.setTitle(getTitle());
		command.setContent(getContent());
		command.setExpirationDate(getExpirationDate());
		command.setPeopleRecruited(getPeopleRecruited());
		command.setBookMark(getBookMark());
		command.setIsActive(getIsActive());
		return command;
	}


}
