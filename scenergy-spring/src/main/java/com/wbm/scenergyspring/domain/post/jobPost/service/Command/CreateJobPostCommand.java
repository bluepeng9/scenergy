package com.wbm.scenergyspring.domain.post.jobPost.service.Command;

import java.time.LocalDateTime;
import java.util.List;

import com.wbm.scenergyspring.domain.post.jobPost.entity.IsActive;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateJobPostCommand {
	Long userId;
	String title;
	String content;
	LocalDateTime expirationDate;
	Long peopleRecruited;
	Long bookMark;
	IsActive isActive;
	List<Long> genreTagIds;
	List<Long> instrumentTagIds;
	List<Long> locationTagIds;
}
