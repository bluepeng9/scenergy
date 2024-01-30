package com.wbm.scenergyspring.domain.post.jobPost.service.Command;

import java.util.ArrayList;
import java.util.List;

import com.wbm.scenergyspring.domain.post.jobPost.entity.JobPost;
import com.wbm.scenergyspring.domain.post.jobPost.entity.JobPostGenreTag;
import com.wbm.scenergyspring.domain.tag.entity.GenreTag;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JobPostGenreTagCommand {

	private Long id;
	private JobPost jobPost;
	private GenreTag genreTag;

	public static List<JobPostGenreTagCommand> createJobPostGenreTagCommand(List<JobPostGenreTag> jobPostGenreTags) {
		List<JobPostGenreTagCommand> list = new ArrayList<>();
		for (JobPostGenreTag jobPostGenreTag : jobPostGenreTags) {
			JobPostGenreTagCommand jobPostGenreTagCommand = JobPostGenreTagCommand.builder()
				.id(jobPostGenreTag.getId())
				.genreTag(jobPostGenreTag.getGenreTag())
				.jobPost(jobPostGenreTag.getJobPost())
				.build();
			list.add(jobPostGenreTagCommand);
		}
		return list;
	}
}
