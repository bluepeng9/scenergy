package com.wbm.scenergyspring.domain.post.jobPost.service.Command;

import java.util.ArrayList;
import java.util.List;

import com.wbm.scenergyspring.domain.post.jobPost.entity.JobPost;
import com.wbm.scenergyspring.domain.post.jobPost.entity.JobPostInstrumentTag;
import com.wbm.scenergyspring.domain.tag.entity.InstrumentTag;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JobPostInstrumentCommand {

	private Long id;
	private JobPost jobPost;
	private InstrumentTag instrumentTag;

	public static List<JobPostInstrumentCommand> createJobPostGenreTagCommand(List<JobPostInstrumentTag> jobPostInstrumentTags) {
		List<JobPostInstrumentCommand> list = new ArrayList<>();
		for (JobPostInstrumentTag jobPostInstrumentTag : jobPostInstrumentTags) {
			JobPostInstrumentCommand jobPostInstrumentCommand = JobPostInstrumentCommand.builder()
				.id(jobPostInstrumentTag.getId())
				.instrumentTag(jobPostInstrumentTag.getInstrumentTag())
				.jobPost(jobPostInstrumentTag.getJobPost())
				.build();
			list.add(jobPostInstrumentCommand);
		}
		return list;
	}
}
