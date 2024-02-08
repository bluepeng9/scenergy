package com.wbm.scenergyspring.domain.post.jobPost.controller.response;

import java.time.LocalDateTime;

import com.wbm.scenergyspring.domain.post.jobPost.entity.IsActive;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetJobPostControllerResponse {
	IsActive isActive;
	String nickname;
	String title;
	String content;
	LocalDateTime expirationDate;
	Long totalCount;

	public static GetJobPostControllerResponse from (GetJobPostCommandResponse commandResponse) {
		return GetJobPostControllerResponse.builder()
			.isActive(commandResponse.isActive)
			.nickname(commandResponse.userDto.nickname)
			.title(commandResponse.title)
			.content(commandResponse.content)
			.expirationDate(commandResponse.expirationDate)
			.totalCount(commandResponse.totalCount)
			.build();
	}

}
