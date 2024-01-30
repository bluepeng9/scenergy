package com.wbm.scenergyspring.domain.post.jobPost.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wbm.scenergyspring.domain.post.jobPost.controller.response.GetAllJobPostResponse;
import com.wbm.scenergyspring.domain.post.jobPost.controller.response.GetJobPostCommandResponse;
import com.wbm.scenergyspring.domain.post.jobPost.controller.response.GetJobPostResponse;
import com.wbm.scenergyspring.domain.post.jobPost.entity.JobPost;
import com.wbm.scenergyspring.domain.post.jobPost.entity.JobPostGenreTag;
import com.wbm.scenergyspring.domain.post.jobPost.entity.JobPostInstrumentTag;
import com.wbm.scenergyspring.domain.post.jobPost.entity.JobPostLocationTag;
import com.wbm.scenergyspring.domain.post.jobPost.repository.JobPostGenreTagRepository;
import com.wbm.scenergyspring.domain.post.jobPost.repository.JobPostInstrumentrepository;
import com.wbm.scenergyspring.domain.post.jobPost.repository.JobPostLocationRepository;
import com.wbm.scenergyspring.domain.post.jobPost.repository.JobPostRepository;
import com.wbm.scenergyspring.domain.post.jobPost.service.Command.CreateJobPostCommand;
import com.wbm.scenergyspring.domain.post.jobPost.service.Command.DeleteJobPostCommand;
import com.wbm.scenergyspring.domain.post.jobPost.service.Command.GetJobPostCommand;
import com.wbm.scenergyspring.domain.post.jobPost.service.Command.JobPostGenreTagCommand;
import com.wbm.scenergyspring.domain.post.jobPost.service.Command.JobPostInstrumentCommand;
import com.wbm.scenergyspring.domain.post.jobPost.service.Command.JobPostLocationCommand;
import com.wbm.scenergyspring.domain.post.jobPost.service.Command.UpdateJobPostcommand;
import com.wbm.scenergyspring.domain.tag.entity.GenreTag;
import com.wbm.scenergyspring.domain.tag.entity.InstrumentTag;
import com.wbm.scenergyspring.domain.tag.entity.LocationTag;
import com.wbm.scenergyspring.domain.tag.repository.GenreTagRepository;
import com.wbm.scenergyspring.domain.tag.repository.InstrumentTagRepository;
import com.wbm.scenergyspring.domain.tag.repository.LocationTagRepository;
import com.wbm.scenergyspring.domain.user.entity.User;
import com.wbm.scenergyspring.domain.user.repository.UserRepository;
import com.wbm.scenergyspring.global.exception.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JobPostService {

	final UserRepository userRepository;
	final JobPostRepository jobPostRepository;

	final GenreTagRepository genreTagRepository;
	final InstrumentTagRepository instrumentTagRepository;
	final LocationTagRepository locationTagRepository;

	final JobPostGenreTagRepository jobPostGenreTagRepository;
	final JobPostInstrumentrepository jobPostInstrumentrepository;
	final JobPostLocationRepository jobPostLocationRepository;

	@Transactional(readOnly = false)
	public Long createJobPost(CreateJobPostCommand command) {
		User postUser = userRepository.getReferenceById(command.getUserId());
		JobPost newJobPost = JobPost.createNewJobPost(
			postUser,
			command.getTitle(),
			command.getContent(),
			command.getExpirationDate(),
			command.getPeopleRecruited(),
			command.getBookMark(),
			command.getIsActive()
		);
		JobPost result = jobPostRepository.save(newJobPost);
		createJobPostGenreTag(command.getGenreTagIds(), result);
		createJobPostInstrumentTag(command.getInstrumentTagIds(), result);
		createJobPostLocationTag(command.getLocationTagIds(), result);
		return result.getId();
	}

	@Transactional(readOnly = false)
	public Long updateJobPost(UpdateJobPostcommand command) {
		JobPost jobPost = jobPostRepository.findById(command.getJobPostId())
			.orElseThrow(() -> new EntityNotFoundException("수정 실패"));
		jobPost.updateJobPost(
			command.getJobPostId(),
			command.getTitle(),
			command.getContent(),
			command.getExpirationDate(),
			command.getPeopleRecruited(),
			command.getBookMark(),
			command.getIsActive()
		);

	return command.getJobPostId();
	}

	@Transactional(readOnly = false)
	public Long deleteJobPost(DeleteJobPostCommand command) {
		jobPostRepository.deleteById(command.getJobPostId());
		return command.getJobPostId();
	}

	public GetJobPostCommandResponse getJobPost(GetJobPostCommand command) {
		JobPost jobPost = jobPostRepository.findById(command.getJobPostId())
			.orElseThrow(() -> new EntityNotFoundException("없는 게시글"));
		User user = jobPost.getUserId();
		GetJobPostCommandResponse getJobPostCommandResponse = GetJobPostCommandResponse.from(jobPost);
		return getJobPostCommandResponse;
	}

	public List<GetAllJobPostResponse> getAllJobPostList() {
		List<GetAllJobPostResponse> jobPosts = new ArrayList<>();
		for (JobPost jobPost : jobPostRepository.findAll()) {
			User user = jobPost.getUserId();
			GetAllJobPostResponse response = GetAllJobPostResponse.builder()
				.jobPostId(jobPost.getId())
				.userId(user.getId())
				.title(jobPost.getTitle())
				.content(jobPost.getContent())
				.expirationDate(jobPost.getExpirationDate())
				.peopleRecruited(jobPost.getPeopleRecrutied())
				.bookMark(jobPost.getBookMark())
				.isActive(jobPost.getIsActive())
				.genreTags(JobPostGenreTagCommand.createJobPostGenreTagCommand(jobPost.getJobPostGenreTags()))
				.instrumentTags(JobPostInstrumentCommand.createJobPostGenreTagCommand(jobPost.getJobPostInstrumentTags()))
				.locationTags(JobPostLocationCommand.createJobPostLocationTagCommand(jobPost.getJobPostLocationTags()))
				.build();

			jobPosts.add(response);
		}
		return jobPosts;
	}

	public void createJobPostGenreTag(List<Long> genreTags, JobPost jobPost) {
		List<JobPostGenreTag> jobPostGenreTags = jobPost.getJobPostGenreTags();
		if (jobPostGenreTags != null) {
			jobPost.deleteJobPostGenreTags();
			jobPostGenreTags.clear();
		}

		for (Long genreTagId : genreTags) {
			GenreTag genreTag = genreTagRepository.findById(genreTagId)
				.orElseThrow(() -> new EntityNotFoundException("존재하지 않는 장르태그"));

			JobPostGenreTag jobPostGenreTag = new JobPostGenreTag();
			jobPostGenreTag.updateJobPost(jobPost);
			jobPostGenreTag.updateGenreTag(genreTag);

			jobPostGenreTagRepository.save(jobPostGenreTag);

			jobPostGenreTags.add(jobPostGenreTag);
		}
		jobPost.updateJobPostGenreTags(jobPostGenreTags);
	}

	public void createJobPostInstrumentTag(List<Long> instrumentTags, JobPost jobPost) {
		List<JobPostInstrumentTag> jobPostInstrumentTags = jobPost.getJobPostInstrumentTags();
		if (jobPostInstrumentTags != null) {
			jobPost.deleteJobPostInstrumentTags();
			jobPostInstrumentTags.clear();
		}

		for (Long instrumentId : instrumentTags) {
			InstrumentTag instrumentTag = instrumentTagRepository.findById(instrumentId)
				.orElseThrow(() -> new EntityNotFoundException("존재하지 않는 장르태그"));

			JobPostInstrumentTag jobPostInstrumentTag = new JobPostInstrumentTag();
			jobPostInstrumentTag.updateJobPost(jobPost);
			jobPostInstrumentTag.updateInstrumentTag(instrumentTag);

			jobPostInstrumentrepository.save(jobPostInstrumentTag);

			jobPostInstrumentTags.add(jobPostInstrumentTag);
		}
		jobPost.updateJobPostInstrumentTags(jobPostInstrumentTags);
	}

	public void createJobPostLocationTag(List<Long> locationTags, JobPost jobPost) {
		List<JobPostLocationTag> jobPostLocationTags = jobPost.getJobPostLocationTags();
		if (jobPostLocationTags != null) {
			jobPost.deleteJobPostLocationTags();
			jobPostLocationTags.clear();
		}

		for (Long locationId : locationTags) {
			LocationTag locationTag = locationTagRepository.findById(locationId)
				.orElseThrow(() -> new EntityNotFoundException("존재하지 않는 장르태그"));

			JobPostLocationTag jobPostLocationTag = new JobPostLocationTag();
			jobPostLocationTag.updateJobPost(jobPost);
			jobPostLocationTag.updateLocationTag(locationTag);

			jobPostLocationRepository.save(jobPostLocationTag);

			jobPostLocationTags.add(jobPostLocationTag);
		}
		jobPost.updateJobPostLocationTags(jobPostLocationTags);
	}

}
