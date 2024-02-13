package com.wbm.scenergyspring.domain.post.jobPost.service;


import com.wbm.scenergyspring.domain.post.jobPost.controller.request.UpdateJobPostRequest;
import com.wbm.scenergyspring.domain.post.jobPost.controller.response.GetJobPostCommandResponse;
import com.wbm.scenergyspring.domain.post.jobPost.controller.response.SearchAllJobPostResponse;
import com.wbm.scenergyspring.domain.post.jobPost.entity.*;
import com.wbm.scenergyspring.domain.post.jobPost.repository.*;
import com.wbm.scenergyspring.domain.post.jobPost.service.Command.*;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobPostService {

	final UserRepository userRepository;
	final JobPostRepository jobPostRepository;

	final GenreTagRepository genreTagRepository;
	final InstrumentTagRepository instrumentTagRepository;
	final LocationTagRepository locationTagRepository;

	final JobPostGenreTagRepository jobPostGenreTagRepository;
	final JobPostInstrumentRepository jobPostInstrumentrepository;
	final JobPostLocationRepository jobPostLocationRepository;
	final JobPostApplyRepository jobPostApplyRepository;
	final JobBookMarkRepository jobBookMarkRepository;

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
	public String ApplyJobPost(ApplyJobPostCommand command) {
		JobPost jobPost = jobPostRepository.findById(command.getJobPostId())
			.orElseThrow(() -> new EntityNotFoundException("존재하지 않는 공고입니다."));
		User user = userRepository.findById(command.getUserId())
			.orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저입니다."));
		if (jobPostApplyRepository.findByJobPostAndUser(jobPost, user) == null) {
			jobPost.plusApplicant();
			JobPostApply jobPostApply = new JobPostApply(jobPost, user);
			jobPostApplyRepository.save(jobPostApply);
			return "지원완료";
		}
		return "이미 지원한 공고";
	}

	@Transactional(readOnly = false)
	public String cancleApplication(CancleApplicationCommand command) {
		JobPost jobPost = jobPostRepository.findById(command.getJobPostId())
			.orElseThrow(() -> new EntityNotFoundException("유효하지 않은 공고입니다."));
		JobPostApply jobPostApply = jobPostApplyRepository.findApplyByJobPost(jobPost);
		jobPostApplyRepository.delete(jobPostApply);
		jobPost.minusApplicant();
		return "지원취소";
	}


	@Transactional(readOnly = false)
	public String BookMarkJobPost(BookMarkCommand command) {
		JobPost jobPost = jobPostRepository.findById(command.getJobPostId())
			.orElseThrow(() -> new EntityNotFoundException("유효하지 않은 공고입니다."));
		User user = userRepository.findById(command.getUserId())
			.orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저입니다."));
		if (jobBookMarkRepository.findByJobPostAndUser(jobPost, user) == null) {
			JobBookMark jobBookMark = new JobBookMark(jobPost, user);
			jobPost.plusBookMark();
			jobBookMarkRepository.save(jobBookMark);
			return "북마크";
		}
		return "이미 북마크 되어있는 게시글";
	}

	@Transactional(readOnly = false)
	public String deleteBookMarkJobPost(DeleteBookMarkCommand command) {
		JobPost jobPost = jobPostRepository.findById(command.getJobPostId())
			.orElseThrow(() -> new EntityNotFoundException("유효하지 않은 공고입니다."));
		JobBookMark jobBookMark = jobBookMarkRepository.findBookMarkByJobPost(jobPost);
		jobBookMarkRepository.delete(jobBookMark);
		jobPost.minusBookMark();
		return "북마크 취소";
	}


	@Transactional(readOnly = false)
	public boolean updateJobPost(Long id, UpdateJobPostRequest request) {
		JobPost jobPost = jobPostRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("수정 실패"));

		if (request.getGenreTags() != null)
			createJobPostGenreTag(request.getGenreTags(), jobPost);
		if(request.getInstrumentTags() != null)
			createJobPostInstrumentTag(request.getInstrumentTags(),jobPost);
		if(request.getLocationTags() != null)
			createJobPostLocationTag(request.getLocationTags(),jobPost);

		UpdateJobPostCommand command = request.updateJobPostCommand();
		jobPost.updateJobPost(command);
		return true;
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

	public List<GetJobPostCommandResponse> getBookMarkJobPost(Long id) {
		List<GetJobPostCommandResponse> list = new ArrayList<>();
		for (JobPost jobPost: jobPostRepository.findAllBookMark(id)) {
			User user = jobPost.getUserId();
			GetJobPostCommandResponse getJobPostCommandResponse = GetJobPostCommandResponse.from(jobPost);
			list.add(getJobPostCommandResponse);
		}
		return list;
	}

	public List<GetJobPostCommandResponse> getMyJobPost(Long id) {
		List<GetJobPostCommandResponse> list = new ArrayList<>();
		for (JobPost jobPost: jobPostRepository.findAllByPost(id)) {
			User user = jobPost.getUserId();
			GetJobPostCommandResponse getJobPostCommandResponse = GetJobPostCommandResponse.from(jobPost);
				list.add(getJobPostCommandResponse);
		}
		return list;
	}

	public List<GetJobPostCommandResponse> getAllMyApply(Long id) {
		List<GetJobPostCommandResponse> list = new ArrayList<>();
		for (JobPost jobPost : jobPostRepository.findAllApplyPostByUser(id)) {
			User user = jobPost.getUserId();
			GetJobPostCommandResponse getJobPostCommandResponse = GetJobPostCommandResponse.from(jobPost);
			list.add(getJobPostCommandResponse);
		}
		return list;
	}

	public List<GetJobPostCommandResponse> getAllJobPostList() {
		List<GetJobPostCommandResponse> jobPosts = new ArrayList<>();
		for (JobPost jobPost : jobPostRepository.findAll()) {
			User user = jobPost.getUserId();
			GetJobPostCommandResponse getJobPostCommandResponse = GetJobPostCommandResponse.from(jobPost);
			jobPosts.add(getJobPostCommandResponse);

		}
		return jobPosts;
	}

	public void createJobPostGenreTag(List<Long> genreTags, JobPost jobPost) {
		List<JobPostGenreTag> jobPostGenreTags = jobPost.getJobPostGenreTags();
			jobPost.deleteJobPostGenreTags();

		for (Long genreTagId : genreTags) {
			GenreTag genreTag = genreTagRepository.findById(genreTagId)
				.orElseThrow(() -> new EntityNotFoundException("존재하지 않는 장르태그"));

			JobPostGenreTag jobPostGenreTag = new JobPostGenreTag();
			jobPostGenreTag.updateJobPost(jobPost);
			jobPostGenreTag.updateGenreTag(genreTag);

			jobPostGenreTags.add(jobPostGenreTag);
		}
		jobPost.updateJobPostGenreTags(jobPostGenreTags);
	}

	public void createJobPostInstrumentTag(List<Long> instrumentTags, JobPost jobPost) {
		List<JobPostInstrumentTag> jobPostInstrumentTags = jobPost.getJobPostInstrumentTags();

			jobPost.deleteJobPostInstrumentTags();

		for (Long instrumentId : instrumentTags) {
			InstrumentTag instrumentTag = instrumentTagRepository.findById(instrumentId)
				.orElseThrow(() -> new EntityNotFoundException("존재하지 않는 악기태그"));

			JobPostInstrumentTag jobPostInstrumentTag = new JobPostInstrumentTag();
			jobPostInstrumentTag.updateJobPost(jobPost);
			jobPostInstrumentTag.updateInstrumentTag(instrumentTag);

			jobPostInstrumentTags.add(jobPostInstrumentTag);
		}
		jobPost.updateJobPostInstrumentTags(jobPostInstrumentTags);

		}
	public void createJobPostLocationTag(List<Long> locationTags, JobPost jobPost) {
		List<JobPostLocationTag> jobPostLocationTags = jobPost.getJobPostLocationTags();
		if (jobPostLocationTags != null) {
			jobPost.deleteJobPostLocationTags();
		}

		for (Long locationId : locationTags) {
			LocationTag locationTag = locationTagRepository.findById(locationId)
				.orElseThrow(() -> new EntityNotFoundException("존재하지 않는 지역태그"));

			JobPostLocationTag jobPostLocationTag = new JobPostLocationTag();
			jobPostLocationTag.updateJobPost(jobPost);
			jobPostLocationTag.updateLocationTag(locationTag);

			jobPostLocationTags.add(jobPostLocationTag);
		}
		jobPost.updateJobPostLocationTags(jobPostLocationTags);
	}

	public List<SearchAllJobPostResponse> searchAllJobPost(SearchAllJobPostCommand command) {
		if (command.getGt().isEmpty())
			command.setGt(null);
		if (command.getIt().isEmpty())
			command.setIt(null);
		if (command.getLt().isEmpty())
			command.setLt(null);
		List<JobPost> jobPosts = jobPostRepository.searchAllJobPost(command.getName(), command.getGt(), command.getIt(), command.getLt());
		List<SearchAllJobPostResponse> result = new ArrayList<>();
		for (JobPost post : jobPosts) {
			SearchAllJobPostResponse response = SearchAllJobPostResponse.builder()
					.jobPostId(post.getId())
					.userId(post.getUserId().getId())
					.title(post.getTitle())
					.nickname(post.getWriter())
					.content(post.getContent())
					.expirationDate(post.getExpirationDate())
					.peopleRecruited(post.getPeopleRecruited())
					.bookMark(post.getBookMark())
					.totalApplicant(post.getTotalApplicant())
					.isActive(post.getIsActive())
					.jgt(JobPostGenreTagCommand.createJobPostGenreTagCommand(post.getJobPostGenreTags()))
					.jit(JobPostInstrumentCommand.createJobPostInstrumentTagCommand(post.getJobPostInstrumentTags()))
					.jlt(JobPostLocationCommand.createJobPostLocationTagCommand(post.getJobPostLocationTags()))
					.build();
			result.add(response);
		}
		return result;
	}

}
