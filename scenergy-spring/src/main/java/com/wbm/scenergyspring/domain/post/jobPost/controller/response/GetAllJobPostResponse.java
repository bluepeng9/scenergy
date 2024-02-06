package com.wbm.scenergyspring.domain.post.jobPost.controller.response;

import java.time.LocalDateTime;
import java.util.List;

import com.wbm.scenergyspring.domain.post.jobPost.entity.IsActive;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetAllJobPostResponse {
	Long jobPostId;
	Long userId;
	String title;
	String content;
	LocalDateTime expirationDate;
	Long peopleRecruited;
	Long bookMark;
	IsActive isActive;
	List<GenreTagResponse> genreTags;
	List<InstrumentTagResponse> instrumentTags;
	List<LocationTagResponse> locationTags;
}
