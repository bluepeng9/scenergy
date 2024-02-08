package com.wbm.scenergyspring.domain.post.jobPost.controller.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.wbm.scenergyspring.domain.post.jobPost.entity.IsActive;
import com.wbm.scenergyspring.domain.post.jobPost.entity.JobPost;
import com.wbm.scenergyspring.domain.post.jobPost.entity.JobPostApply;
import com.wbm.scenergyspring.domain.post.jobPost.entity.JobPostGenreTag;
import com.wbm.scenergyspring.domain.post.jobPost.entity.JobPostInstrumentTag;
import com.wbm.scenergyspring.domain.post.jobPost.entity.JobPostLocationTag;
import com.wbm.scenergyspring.domain.tag.entity.GenreTag;
import com.wbm.scenergyspring.domain.tag.entity.InstrumentTag;
import com.wbm.scenergyspring.domain.tag.entity.LocationTag;
import com.wbm.scenergyspring.domain.user.entity.Gender;
import com.wbm.scenergyspring.domain.user.entity.Role;
import com.wbm.scenergyspring.domain.user.entity.User;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetAllJobPostCommandResponse {
	GetAllUserDto userDto;

	String title;
	String content;

	LocalDateTime expirationDate;
	IsActive isActive;

	Long peopleRecruited;

	Long bookMark;

	Long totalCount;

	private List<GetAllInstrumentTagDto> jobPostInstrumentTags = new ArrayList<>();

	private List<GetAllLocationTagDto> jobPostLocationTags = new ArrayList<>();

	private List<GetAllGenreTagDto> jobPostGenreTags = new ArrayList<>();

	private List<GetAllJobApplicantDto> jobApplicant = new ArrayList<>();

	public static GetAllJobPostCommandResponse from(JobPost jobPost) {
		User user = jobPost.getUserId();
		List<JobPostInstrumentTag> jobPostInstrumentTags1 = jobPost.getJobPostInstrumentTags();
		List<GetAllInstrumentTagDto> instrumentTags = new ArrayList<>();
		for (JobPostInstrumentTag tag : jobPostInstrumentTags1) {
			GetAllInstrumentTagDto from = GetAllInstrumentTagDto.from(tag.getInstrumentTag());
			instrumentTags.add(from);
		}
		List<JobPostGenreTag> jobPostGenreTags1 = jobPost.getJobPostGenreTags();
		List<GetAllGenreTagDto> genreTags = new ArrayList<>();
		for (JobPostGenreTag tag : jobPostGenreTags1) {
			GetAllGenreTagDto from = GetAllGenreTagDto.from(tag.getGenreTag());
			genreTags.add(from);
		}

		List<JobPostLocationTag> jobPostLocationTags1 = jobPost.getJobPostLocationTags();
		List<GetAllLocationTagDto> locationTags = new ArrayList<>();
		for (JobPostLocationTag tag : jobPostLocationTags1) {
			GetAllLocationTagDto from = GetAllLocationTagDto.from(tag.getLocationTag());
			locationTags.add(from);
		}
		Long cnt = 0L;
		List<JobPostApply> jobApplicantList = jobPost.getJobApplicant();
		List<GetAllJobApplicantDto> applicant = new ArrayList<>();
		for(JobPostApply jobApplicant : jobApplicantList) {
			GetAllJobApplicantDto from = GetAllJobApplicantDto.from(jobApplicant.getUser());
			cnt += 1;
			applicant.add(from);
		}

		return GetAllJobPostCommandResponse.builder()
			.jobPostInstrumentTags(instrumentTags)
			.jobPostGenreTags(genreTags)
			.jobPostLocationTags(locationTags)
			.jobApplicant(applicant)
			.userDto(GetAllUserDto.builder()
				.id(user.getId())
				.email(user.getEmail())
				.nickname(user.getNickname())
				.build())
			.title(jobPost.getTitle())
			.content(jobPost.getContent())
			.expirationDate(jobPost.getExpirationDate())
			.isActive(jobPost.getIsActive())
			.peopleRecruited(jobPost.getPeopleRecruited())
			.bookMark(jobPost.getBookMark())
			.totalCount(cnt)
			.build();
	}
}

@Data
@Builder
class GetAllUserDto {
	Long id;
	String email;
	String nickname;
}

@Data
@Builder
class GetAllLocationTagDto {

	Long id;
	String locationName;

	static GetAllLocationTagDto from (LocationTag locationTag) {
		return GetAllLocationTagDto.builder()
			.id(locationTag.getId())
			.locationName(locationTag.getLocationName())
			.build();
	}
}

@Data
@Builder
class GetAllGenreTagDto {

	Long id;
	String genreName;

	static GetAllGenreTagDto from(GenreTag genreTag) {
		return GetAllGenreTagDto.builder()
			.id(genreTag.getId())
			.genreName(genreTag.getGenreName())
			.build();
	}
}
@Data
@Builder
class GetAllInstrumentTagDto {
	Long id;
	String instrumentName;

	static GetAllInstrumentTagDto from(InstrumentTag instrumentTag) {
		return GetAllInstrumentTagDto.builder()
			.id(instrumentTag.getId())
			.instrumentName(instrumentTag.getInstrumentName())
			.build();
	}
}

@Data
@Builder
class GetAllJobApplicantDto {
	Long id;
	String email;
	String nickname;
	String username;
	Gender gender;
	Role role;

	static GetAllJobApplicantDto from(User user) {
		return GetAllJobApplicantDto.builder()
			.id(user.getId())
			.email(user.getEmail())
			.nickname(user.getNickname())
			.username(user.getUsername())
			.gender(user.getGender())
			.role(user.getRole())
			.build();
	}
}



