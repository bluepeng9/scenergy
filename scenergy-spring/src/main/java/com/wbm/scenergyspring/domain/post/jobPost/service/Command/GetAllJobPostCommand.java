package com.wbm.scenergyspring.domain.post.jobPost.service.Command;

import java.time.LocalDateTime;

import com.wbm.scenergyspring.domain.post.jobPost.entity.IsActive;

import lombok.Data;

@Data
public class GetAllJobPostCommand {
	Long jobPostId;
	Long userId;
	String title;
	String content;
	LocalDateTime expirationDate;
	Long peopleRecruited;
	Long bookMark;
	IsActive isActive;
}
