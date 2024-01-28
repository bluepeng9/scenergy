package com.wbm.scenergyspring.domain.post.jobPost.service.Command;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CreateJobPostCommand {
	Long userId;
	String title;
	String content;
	LocalDateTime expirationDate;
	Long peopleRecruited;
	Long bookMark;
}
