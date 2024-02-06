package com.wbm.scenergyspring.domain.post.jobPost.controller.request;

import java.time.LocalDateTime;
import java.util.List;

import com.wbm.scenergyspring.domain.post.jobPost.entity.IsActive;

import com.wbm.scenergyspring.domain.post.jobPost.entity.JobPost;
import com.wbm.scenergyspring.domain.post.jobPost.service.Command.UpdateJobPostCommand;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateJobPostRequest {

	String title;
	String content;
	LocalDateTime expirationDate;
	Long peopleRecruited;
	Long bookMark;
	IsActive isActive;
	private List<Long> instrumentTags;
	private List<Long> locationTags;
	private List<Long> genreTags;

	public UpdateJobPostCommand toCreateJobPost(JobPost jobPost) {
		UpdateJobPostCommand command = UpdateJobPostCommand.builder()
			.title(title)
			.content(content)
			.expirationDate(expirationDate)
			.peopleRecruited(peopleRecruited)
			.bookMark(bookMark)
			.isActive(isActive)
			// .instrumentTagIds(instrumentTags)
			// .locationTagIds(locationTags)
			// .genreTagIds(genreTags)
			.build();
		return command;
	}

	public UpdateJobPostCommand updateJobPostCommand() {
		UpdateJobPostCommand command = UpdateJobPostCommand.builder()
			.title(title)
			.content(content)
			.expirationDate(expirationDate)
			.peopleRecruited(peopleRecruited)
			.bookMark(bookMark)
			.isActive(isActive)
			// .instrumentTagIds()
			.build();
		return command;

	}
}

