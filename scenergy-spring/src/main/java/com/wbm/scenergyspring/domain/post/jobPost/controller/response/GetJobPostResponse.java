package com.wbm.scenergyspring.domain.post.jobPost.controller.response;

import java.time.LocalDateTime;
import java.util.List;

import com.wbm.scenergyspring.domain.post.jobPost.service.Command.GetJobPostGenreTagCommandResponse;
import com.wbm.scenergyspring.domain.post.jobPost.entity.IsActive;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetJobPostResponse {
	Long jobPostId;
	Long userId;
	String title;
	String content;
	LocalDateTime expirationDate;
	Long peopleRecruited;
	Long bookMark;
	IsActive isActive;
	public List<GetJobPostGenreTagCommandResponse> genreTags;
	public List<InstrumentTagResponse> instrumentTags;
	public List<LocationTagResponse> locationTags;
}

class InstrumentTagResponse {
	Long id;
	String instrumentName;
}

class LocationTagResponse {
	Long ind;
	String locationName;
}
