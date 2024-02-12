package com.wbm.scenergyspring.domain.post.jobPost.service;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.wbm.scenergyspring.IntegrationTest;
import com.wbm.scenergyspring.domain.post.jobPost.controller.request.UpdateJobPostRequest;
import com.wbm.scenergyspring.domain.post.jobPost.controller.response.GetJobPostCommandResponse;
import com.wbm.scenergyspring.domain.post.jobPost.controller.response.SearchAllJobPostResponse;
import com.wbm.scenergyspring.domain.post.jobPost.entity.IsActive;
import com.wbm.scenergyspring.domain.post.jobPost.entity.JobPost;
import com.wbm.scenergyspring.domain.post.jobPost.entity.JobPostGenreTag;
import com.wbm.scenergyspring.domain.post.jobPost.entity.JobPostInstrumentTag;
import com.wbm.scenergyspring.domain.post.jobPost.entity.JobPostLocationTag;
import com.wbm.scenergyspring.domain.post.jobPost.repository.JobBookMarkRepository;
import com.wbm.scenergyspring.domain.post.jobPost.repository.JobPostApplyRepository;
import com.wbm.scenergyspring.domain.post.jobPost.repository.JobPostRepository;
import com.wbm.scenergyspring.domain.post.jobPost.service.Command.ApplyJobPostCommand;
import com.wbm.scenergyspring.domain.post.jobPost.service.Command.BookMarkCommand;
import com.wbm.scenergyspring.domain.post.jobPost.service.Command.CreateJobPostCommand;
import com.wbm.scenergyspring.domain.post.jobPost.service.Command.DeleteJobPostCommand;
import com.wbm.scenergyspring.domain.post.jobPost.service.Command.GetJobPostCommand;
import com.wbm.scenergyspring.domain.post.jobPost.service.Command.SearchAllJobPostCommand;
import com.wbm.scenergyspring.domain.tag.entity.GenreTag;
import com.wbm.scenergyspring.domain.tag.entity.InstrumentTag;
import com.wbm.scenergyspring.domain.tag.entity.LocationTag;
import com.wbm.scenergyspring.domain.tag.repository.GenreTagRepository;
import com.wbm.scenergyspring.domain.tag.repository.InstrumentTagRepository;
import com.wbm.scenergyspring.domain.tag.repository.LocationTagRepository;
import com.wbm.scenergyspring.domain.user.entity.Gender;
import com.wbm.scenergyspring.domain.user.entity.User;
import com.wbm.scenergyspring.domain.user.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@SpringBootTest
class JobPostServiceTest extends IntegrationTest {

	@Autowired
	JobPostService jobPostService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	JobPostRepository jobPostRepository;

	@Autowired
	JobPostApplyRepository jobPostApplyRepository;

	@Autowired
	JobBookMarkRepository jobBookMarkRepository;

	@Autowired
	GenreTagRepository genreTagRepository;

	@Autowired
	InstrumentTagRepository instrumentTagRepository;

	@Autowired
	LocationTagRepository locationTagRepository;

	GenreTag saveGenreTag(String name) {
		GenreTag tag = genreTagRepository.save(GenreTag.createGenreTag(name));
		return tag;
	}

	InstrumentTag saveInstrumentTag(String name) {
		InstrumentTag tag = instrumentTagRepository.save(InstrumentTag.createInstrumentTag(name));
		return tag;
	}

	LocationTag saveLocationTag(String name) {
		LocationTag tag = locationTagRepository.save(LocationTag.createLocationTag(name));
		return tag;
	}

	@Test
	@DisplayName("게시글 추가")
	@Transactional
	void createJobPost() {
		List<InstrumentTag> instrumentTagList = new ArrayList<>();
		InstrumentTag instrumentTag1 = saveInstrumentTag("기타");
		InstrumentTag instrumentTag2 = saveInstrumentTag("드럼");
		InstrumentTag instrumentTag3 = saveInstrumentTag("베이스");
		instrumentTagList.add(instrumentTag1);
		instrumentTagList.add(instrumentTag2);
		instrumentTagList.add(instrumentTag3);

		List<GenreTag> genreTagList = new ArrayList<>();
		GenreTag genreTag1 = saveGenreTag("Jazz");
		GenreTag genreTag2 = saveGenreTag("Pop");
		GenreTag genreTag3 = saveGenreTag("Hiphop");
		genreTagList.add(genreTag1);
		genreTagList.add(genreTag2);
		genreTagList.add( genreTag3);

		List<LocationTag> locationTagList = new ArrayList<>();
		LocationTag locationTag1 = saveLocationTag("서울");
		LocationTag locationTag2 = saveLocationTag("인천");
		LocationTag locationTag3 = saveLocationTag("대전");
		locationTagList.add(locationTag1);
		locationTagList.add(locationTag2);
		locationTagList.add(locationTag3);


		// given
		User user = User.createNewUser(
			"aaa@naver.com",
			"aaaaa",
			"aaaaa",
			Gender.FEMALE,
			"aaa"
		);
		User saveUser = userRepository.save(user);

		JobPost newJobPost = JobPost.createNewJobPost(
			user,
			"aaa",
			"aaaa",
			LocalDateTime.parse("2024-02-01T10:00:00"),
			4L,
			0L,
			IsActive.active
		);

		List<Long> genreTags = Arrays.asList(genreTag1.getId(),genreTag2.getId(),genreTag3.getId());
		List<Long> instrumentTags = Arrays.asList(instrumentTag1.getId(), instrumentTag2.getId(), instrumentTag3.getId());
		List<Long> locationTags = Arrays.asList(locationTag1.getId(), locationTag2.getId(), locationTag3.getId());

		CreateJobPostCommand command = CreateJobPostCommand.builder()
			.userId(saveUser.getId())
			.title(newJobPost.getTitle())
			.content(newJobPost.getContent())
			.expirationDate(newJobPost.getExpirationDate())
			.peopleRecruited(newJobPost.getPeopleRecruited())
			.bookMark(newJobPost.getBookMark())
			.isActive(newJobPost.getIsActive())
			.locationTagIds(locationTags)
			.genreTagIds(genreTags)
			.instrumentTagIds(instrumentTags)
			.build();

		// when
		Long id = jobPostService.createJobPost(command);

		// then
		JobPost findJobPost = jobPostRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("없는 게시글"));

		assertThat(id).isEqualTo(findJobPost.getId());

		List<JobPostGenreTag> genreTagsInJobPost = findJobPost.getJobPostGenreTags();
		assertThat(genreTagsInJobPost).isNotNull();
		;
		assertThat(3).isEqualTo(genreTagsInJobPost.size());

		List<JobPostInstrumentTag> instrumentTagsInJobPost  = findJobPost.getJobPostInstrumentTags();
		assertThat(instrumentTagsInJobPost).isNotNull();
		;
		assertThat(3).isEqualTo(instrumentTagsInJobPost.size());

		List<JobPostLocationTag> locationTagInJobPost  = findJobPost.getJobPostLocationTags();
		assertThat(locationTagInJobPost).isNotNull();
		;
		assertThat(3).isEqualTo(locationTagInJobPost.size());

		jobPostRepository.deleteById(id);
	}

	@Test
	@DisplayName("게시글 수정")
	@Transactional
	void updateJobPost() {
		// given
		List<InstrumentTag> instrumentTagList = new ArrayList<>();
		InstrumentTag instrumentTag1 = saveInstrumentTag("기타");
		InstrumentTag instrumentTag2 = saveInstrumentTag("드럼");
		InstrumentTag instrumentTag3 = saveInstrumentTag("베이스");
		instrumentTagList.add(instrumentTag1);
		instrumentTagList.add(instrumentTag2);
		instrumentTagList.add(instrumentTag3);

		List<GenreTag> genreTagList = new ArrayList<>();
		GenreTag genreTag1 = saveGenreTag("Jazz");
		GenreTag genreTag2 = saveGenreTag("Pop");
		GenreTag genreTag3 = saveGenreTag("Hiphop");
		genreTagList.add(genreTag1);
		genreTagList.add(genreTag2);
		genreTagList.add( genreTag3);

		List<LocationTag> locationTagList = new ArrayList<>();
		LocationTag locationTag1 = saveLocationTag("서울");
		LocationTag locationTag2 = saveLocationTag("인천");
		LocationTag locationTag3 = saveLocationTag("대전");
		locationTagList.add(locationTag1);
		locationTagList.add(locationTag2);
		locationTagList.add(locationTag3);

		User user = User.createNewUser(
			"aaa@naver.com",
			"aaaaa",
			"aaaaa",
			Gender.FEMALE,
			"aaa"
		);

		User saveUser = userRepository.save(user);

		JobPost newJobPost = JobPost.createNewJobPost(
			user,
			"aaa",
			"aaaa",
			LocalDateTime.parse("2024-02-01T10:00:00"),
			4L,
			0L,
			IsActive.active
		);

		List<Long> genreTags = Arrays.asList(genreTag1.getId(),genreTag2.getId(),genreTag3.getId());
		List<Long> instrumentTags = Arrays.asList(instrumentTag1.getId(), instrumentTag2.getId(), instrumentTag3.getId());
		List<Long> locationTags = Arrays.asList(locationTag1.getId(), locationTag2.getId(), locationTag3.getId());

		CreateJobPostCommand createCommand = CreateJobPostCommand.builder()
			.userId(saveUser.getId())
			.title(newJobPost.getTitle())
			.content(newJobPost.getContent())
			.expirationDate(newJobPost.getExpirationDate())
			.peopleRecruited(newJobPost.getPeopleRecruited())
			.bookMark(newJobPost.getBookMark())
			.isActive(newJobPost.getIsActive())
			.locationTagIds(locationTags)
			.genreTagIds(genreTags)
			.instrumentTagIds(instrumentTags)
			.build();

		Long id = jobPostService.createJobPost(createCommand);

		UpdateJobPostRequest updateRequest = UpdateJobPostRequest.builder()
			.title("bbb")
			.content("bbb")
			.expirationDate(LocalDateTime.parse("2024-02-02T10:00:00"))
			.peopleRecruited(5L)
			.bookMark(1L)
			.isActive(IsActive.active)
			.genreTags(Arrays.asList(genreTag1.getId()))
			.locationTags(Arrays.asList(locationTag1.getId()))
			.instrumentTags(Arrays.asList(instrumentTag1.getId()))
			.build();

		// when
		boolean result = jobPostService.updateJobPost(id, updateRequest);

		// then
		assertThat(result).isTrue();

		JobPost updatedJobPost = jobPostRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("없는 게시글"));

		assertThat(updatedJobPost.getTitle()).isEqualTo("bbb");
		assertThat(updatedJobPost.getContent()).isEqualTo("bbb");
		assertThat(updatedJobPost.getExpirationDate()).isEqualTo(LocalDateTime.parse("2024-02-02T10:00:00"));
		assertThat(updatedJobPost.getPeopleRecruited()).isEqualTo(5L);
		assertThat(updatedJobPost.getBookMark()).isEqualTo(1L);
		assertThat(updatedJobPost.getIsActive()).isEqualTo(IsActive.active);

		List<JobPostGenreTag> updatedGenreTagsInJobPost = updatedJobPost.getJobPostGenreTags();
		assertThat(updatedGenreTagsInJobPost).isNotNull();
		assertThat(updatedGenreTagsInJobPost).hasSize(1);

		List<JobPostInstrumentTag> updatedInstrumentTagsInJobPost = updatedJobPost.getJobPostInstrumentTags();
		assertThat(updatedInstrumentTagsInJobPost).isNotNull();
		assertThat(updatedInstrumentTagsInJobPost).hasSize(1);

		List<JobPostLocationTag> updatedLocationTagsInJobPost = updatedJobPost.getJobPostLocationTags();
		assertThat(updatedLocationTagsInJobPost).isNotNull();
		assertThat(updatedLocationTagsInJobPost).hasSize(1);

		jobPostRepository.deleteById(id);
	}

	@Test
	@DisplayName("게시글 삭제")
	@Transactional
	void deleteJobPost() {

		// given
		long now = jobPostRepository.count();

		List<InstrumentTag> instrumentTagList = new ArrayList<>();
		InstrumentTag instrumentTag1 = saveInstrumentTag("기타");
		InstrumentTag instrumentTag2 = saveInstrumentTag("드럼");
		InstrumentTag instrumentTag3 = saveInstrumentTag("베이스");
		instrumentTagList.add(instrumentTag1);
		instrumentTagList.add(instrumentTag2);
		instrumentTagList.add(instrumentTag3);

		List<GenreTag> genreTagList = new ArrayList<>();
		GenreTag genreTag1 = saveGenreTag("Jazz");
		GenreTag genreTag2 = saveGenreTag("Pop");
		GenreTag genreTag3 = saveGenreTag("Hiphop");
		genreTagList.add(genreTag1);
		genreTagList.add(genreTag2);
		genreTagList.add( genreTag3);

		List<LocationTag> locationTagList = new ArrayList<>();
		LocationTag locationTag1 = saveLocationTag("서울");
		LocationTag locationTag2 = saveLocationTag("인천");
		LocationTag locationTag3 = saveLocationTag("대전");
		locationTagList.add(locationTag1);
		locationTagList.add(locationTag2);
		locationTagList.add(locationTag3);

		User user = User.createNewUser(
			"aaa@naver.com",
			"aaaaa",
			"aaaa",
			Gender.FEMALE,
			"aaa"
		);

		User saveUser = userRepository.save(user);

		JobPost newJobPost = JobPost.createNewJobPost(
			user,
			"aaa",
			"aaaa",
			LocalDateTime.parse("2024-02-01T10:00:00"),
			4L,
			0L,
			IsActive.active
		);

		List<Long> genreTags = Arrays.asList(genreTag1.getId(),genreTag2.getId(),genreTag3.getId());
		List<Long> instrumentTags = Arrays.asList(instrumentTag1.getId(), instrumentTag2.getId(), instrumentTag3.getId());
		List<Long> locationTags = Arrays.asList(locationTag1.getId(), locationTag2.getId(), locationTag3.getId());

		CreateJobPostCommand command1 = CreateJobPostCommand.builder()
			.userId(saveUser.getId())
			.title(newJobPost.getTitle())
			.content(newJobPost.getContent())
			.expirationDate(newJobPost.getExpirationDate())
			.peopleRecruited(newJobPost.getPeopleRecruited())
			.bookMark(newJobPost.getBookMark())
			.isActive(newJobPost.getIsActive())
			.locationTagIds(locationTags)
			.genreTagIds(genreTags)
			.instrumentTagIds(instrumentTags)
			.build();

		Long id = jobPostService.createJobPost(command1);
		System.out.println(id);
		// when
		DeleteJobPostCommand command2 = new DeleteJobPostCommand();
		command2.setJobPostId(id);
		jobPostService.deleteJobPost(command2);

		// then
		assertThat(now).isEqualTo(jobPostRepository.count());
	}

	@Test
	@DisplayName("상세 게시글 조회")
	@Transactional
	void getJobPost() {

		// given
		User user = User.createNewUser(
			"aaa@naver.com",
			"aaaaa",
			"aaaa",
			Gender.FEMALE,
			"aaa"
		);

		User saveUser = userRepository.save(user);
		JobPost newJobPost = JobPost.createNewJobPost(
			saveUser,
			"aaa",
			"aaaa",
			LocalDateTime.parse("2024-02-01T10:00:00"),
			4L,
			0L,
			IsActive.active
		);
		jobPostRepository.save(newJobPost);
		GetJobPostCommand command = new GetJobPostCommand();
		long id = newJobPost.getId();;
		command.setJobPostId(id);
		GetJobPostCommandResponse response = jobPostService.getJobPost(command);

		// when
		JobPost findJob = jobPostRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("없는 게시글"));

		// then
		assertThat(response.getTitle()).isEqualTo(findJob.getTitle());
		jobPostRepository.deleteById(id);
	}

	@Test
	@DisplayName("특정 유저의 게시글 조회")
	@Transactional
	void getMyJobPost() {
		User user = User.createNewUser(
			"aaa@naver.com",
			"aaaaa",
			"aaaa",
			Gender.FEMALE,
			"aaa"
		);
		User saveUser = userRepository.save(user);
		long id = saveUser.getId();
		List<GetJobPostCommandResponse> result = jobPostService.getMyJobPost(id);
		int resultSize = result.size();
		System.out.println(resultSize);
		assertThat(resultSize).isEqualTo(jobPostRepository.findAllByPost(id).size());
		userRepository.deleteById(id);
	}

	@Test
	@DisplayName("북마크한 공고보기")
	@Transactional
	void getBookMarkJobPost() {

		// given
		User user = User.createNewUser(
			"aaa@naver.com",
			"aaaaa",
			"aaaa",
			Gender.FEMALE,
			"aaa"
		);
		userRepository.save(user);
		long id = user.getId();

		// when
		List<GetJobPostCommandResponse> result = jobPostService.getBookMarkJobPost(id);

		//then
		assertThat(result.size()).isEqualTo(jobPostRepository.findAllBookMark(id).size());
		userRepository.deleteById(id);

	}

	@Test
	@DisplayName("내 지원공고 보기")
	@Transactional
	void getAllMyApply() {

		// given
		User user = User.createNewUser(
			"aaa@naver.com",
			"aaaaa",
			"aaaa",
			Gender.FEMALE,
			"aaa"
		);
		userRepository.save(user);
		long id = user.getId();

		// when
		List<GetJobPostCommandResponse> result = jobPostService.getAllMyApply(id);

		//then
		assertThat(result.size()).isEqualTo(jobPostRepository.findAllByPost(id).size());
		userRepository.deleteById(id);
	}




	@Test
	@DisplayName("전체 게시글 조회")
	@Transactional
	void getAllJobPostList() {
		List<GetJobPostCommandResponse> result = jobPostService.getAllJobPostList();
		int resultSize = result.size();
		assertThat(resultSize).isEqualTo(jobPostRepository.count());
	}

	@Test
	@DisplayName("공고 지원하기")
	@Transactional
	void ApplyJobPost() {
		// given
		User user = User.createNewUser(
			"aaa@naver.com",
			"aaaaa",
			"aaaa",
			Gender.FEMALE,
			"aaa"
		);

		User saveUser = userRepository.save(user);
		JobPost newJobPost = JobPost.createNewJobPost(
			saveUser,
			"aaa",
			"aaaa",
			LocalDateTime.parse("2024-02-01T10:00:00"),
			4L,
			0L,
			IsActive.active
		);
		jobPostRepository.save(newJobPost);

		// when
		ApplyJobPostCommand command = ApplyJobPostCommand.builder()
			.jobPostId(newJobPost.getId())
			.userId(saveUser.getId())
			.build();

		String result = jobPostService.ApplyJobPost(command);

		// then
		assertThat("지원완료").isEqualTo(result);

	}

	@Test
	@DisplayName("북마크")
	@Transactional
	void BookMarkJobPost() {

		long cnt = jobBookMarkRepository.count();

		// given
		User user = User.createNewUser(
			"aaa@naver.com",
			"aaaaa",
			"aaaa",
			Gender.FEMALE,
			"aaa"
		);

		User saveUser = userRepository.save(user);
		JobPost newJobPost = JobPost.createNewJobPost(
			saveUser,
			"aaa",
			"aaaa",
			LocalDateTime.parse("2024-02-01T10:00:00"),
			4L,
			0L,
			IsActive.active
		);
		jobPostRepository.save(newJobPost);

		// when
		BookMarkCommand bookMarkCommand = BookMarkCommand.builder()
			.jobPostId(newJobPost.getId())
			.userId(saveUser.getId())
			.build();

		// then
		String result = jobPostService.BookMarkJobPost(bookMarkCommand);

		assertThat(result).isEqualTo("북마크");
	}

	@Test
	@DisplayName("구인구직 검색")
	@Transactional
	void searchJobPost() {
		//given
		List<InstrumentTag> instrumentTagList = new ArrayList<>();
		InstrumentTag instrumentTag1 = saveInstrumentTag("기타");
		InstrumentTag instrumentTag2 = saveInstrumentTag("드럼");
		InstrumentTag instrumentTag3 = saveInstrumentTag("베이스");
		instrumentTagList.add(instrumentTag1);
		instrumentTagList.add(instrumentTag2);
		instrumentTagList.add(instrumentTag3);

		List<GenreTag> genreTagList = new ArrayList<>();
		GenreTag genreTag1 = saveGenreTag("Jazz");
		GenreTag genreTag2 = saveGenreTag("Pop");
		GenreTag genreTag3 = saveGenreTag("Hiphop");
		genreTagList.add(genreTag1);
		genreTagList.add(genreTag2);
		genreTagList.add(genreTag3);

		List<LocationTag> locationTagList = new ArrayList<>();
		LocationTag locationTag1 = saveLocationTag("서울");
		LocationTag locationTag2 = saveLocationTag("인천");
		LocationTag locationTag3 = saveLocationTag("대전");
		locationTagList.add(locationTag1);
		locationTagList.add(locationTag2);
		locationTagList.add(locationTag3);


		// given
		User user = User.createNewUser(
				"aaa@naver.com",
				"aaaaa",
				"aaaaa",
				Gender.FEMALE,
				"aaa"
		);
		User saveUser = userRepository.save(user);

		JobPost newJobPost = JobPost.createNewJobPost(
				user,
				"aaa",
				"aaaa",
				LocalDateTime.parse("2024-02-01T10:00:00"),
				4L,
				0L,
				IsActive.active
		);

		List<Long> genreTags = Arrays.asList(genreTag1.getId(), genreTag2.getId(), genreTag3.getId());
		List<Long> instrumentTags = Arrays.asList(instrumentTag1.getId(), instrumentTag2.getId(), instrumentTag3.getId());
		List<Long> locationTags = Arrays.asList(locationTag1.getId(), locationTag2.getId(), locationTag3.getId());

		CreateJobPostCommand command = CreateJobPostCommand.builder()
				.userId(saveUser.getId())
				.title(newJobPost.getTitle())
				.content(newJobPost.getContent())
				.expirationDate(newJobPost.getExpirationDate())
				.peopleRecruited(newJobPost.getPeopleRecruited())
				.bookMark(newJobPost.getBookMark())
				.isActive(newJobPost.getIsActive())
				.locationTagIds(locationTags)
				.genreTagIds(genreTags)
				.instrumentTagIds(instrumentTags)
				.build();
		CreateJobPostCommand command1 = CreateJobPostCommand.builder()
				.userId(saveUser.getId())
				.title(newJobPost.getTitle())
				.content(newJobPost.getContent())
				.expirationDate(newJobPost.getExpirationDate())
				.peopleRecruited(newJobPost.getPeopleRecruited())
				.bookMark(newJobPost.getBookMark())
				.isActive(newJobPost.getIsActive())
				.locationTagIds(new ArrayList<>())
				.genreTagIds(new ArrayList<>())
				.instrumentTagIds(new ArrayList<>())
				.build();

		Long id = jobPostService.createJobPost(command);
		Long id1 = jobPostService.createJobPost(command1);
		//when
		SearchAllJobPostCommand searchCommand1 = SearchAllJobPostCommand.builder()
				.name(jobPostRepository.findById(id).get().getTitle())
				.lt(null)
				.gt(null)
				.it(null)
				.build();
		SearchAllJobPostCommand searchCommand2 = SearchAllJobPostCommand.builder()
				.name(null)
				.lt(locationTags)
				.gt(null)
				.it(null)
				.build();
		System.out.println(jobPostRepository.findById(id).get().getTitle());
		System.out.println(jobPostRepository.findById(id1).get().getTitle());
		List<SearchAllJobPostResponse> result1 = jobPostService.searchAllJobPost(searchCommand1);
		List<SearchAllJobPostResponse> result2 = jobPostService.searchAllJobPost(searchCommand2);
		System.out.println("cnt : " + jobPostRepository.count());
		System.out.println(searchCommand1.getName());
		//then
		assertThat(result1.size()).isEqualTo(2);
		assertThat(result2.size()).isEqualTo(1);
	}


}