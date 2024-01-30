package com.wbm.scenergyspring.domain.post.jobPost.service.Command;

import java.util.ArrayList;
import java.util.List;

import com.wbm.scenergyspring.domain.post.jobPost.entity.JobPost;
import com.wbm.scenergyspring.domain.post.jobPost.entity.JobPostLocationTag;
import com.wbm.scenergyspring.domain.tag.entity.LocationTag;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JobPostLocationCommand {

	private Long id;
	private JobPost jobPost;
	private LocationTag locationTag;

	public static List<JobPostLocationCommand> createJobPostLocationTagCommand(List<JobPostLocationTag> jobPostLocationTags) {
		List<JobPostLocationCommand> list = new ArrayList<>();
		for (JobPostLocationTag jobPostLocationTag : jobPostLocationTags) {
			JobPostLocationCommand jobPostLocationCommand = JobPostLocationCommand.builder()
				.id(jobPostLocationTag.getId())
				.locationTag(jobPostLocationTag.getLocationTag())
				.jobPost(jobPostLocationTag.getJobPost())
				.build();
			list.add(jobPostLocationCommand);
		}
		return list;
	}
}
