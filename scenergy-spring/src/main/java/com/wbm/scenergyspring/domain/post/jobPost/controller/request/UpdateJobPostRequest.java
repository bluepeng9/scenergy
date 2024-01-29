package com.wbm.scenergyspring.domain.post.jobPost.controller.request;

import java.time.LocalDateTime;

import com.wbm.scenergyspring.domain.post.jobPost.entity.IsActive;
import com.wbm.scenergyspring.domain.post.jobPost.service.Command.UpdateJobPostcommand;

import lombok.Data;

@Data
public class UpdateJobPostRequest {

	Long jobPostId;
	String title;
	String content;
	LocalDateTime expirationDate;
	Long peopleRecruited;
	Long bookMark;
	IsActive isActive;

	public UpdateJobPostcommand toUpdateJobPost() {
		UpdateJobPostcommand command = new UpdateJobPostcommand();
		command.setJobPostId(getJobPostId());
		command.setTitle(getTitle());
		command.setContent(getContent());
		command.setExpirationDate(getExpirationDate());
		command.setPeopleRecruited(getPeopleRecruited());
		command.setBookMark(getBookMark());
		command.setIsActive(getIsActive());
		return command;
	}
}
