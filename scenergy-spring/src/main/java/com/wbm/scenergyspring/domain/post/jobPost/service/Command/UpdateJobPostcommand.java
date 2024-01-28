package com.wbm.scenergyspring.domain.post.jobPost.service.Command;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UpdateJobPostcommand {
	Long jobPostId;
	String title;
	String content;
	LocalDateTime expirationDate;
	Long peopleRecruited;
	Long bookMark;
}
