package com.wbm.scenergyspring.domain.post.jobPost.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.wbm.scenergyspring.domain.post.Post;
import com.wbm.scenergyspring.domain.post.jobPost.service.Command.UpdateJobPostCommand;
import com.wbm.scenergyspring.domain.user.entity.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;

@Entity
@Getter
public class JobPost extends Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	User userId;

	String title = getTitle();
	String content = getContent();

	LocalDateTime expirationDate;

	@Enumerated(EnumType.STRING)
	IsActive isActive;

	Long peopleRecruited;

	Long bookMark;

	Long totalApplicant = 0L;

	@OneToMany(mappedBy = "jobPost", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<JobPostInstrumentTag> jobPostInstrumentTags = new ArrayList<>();

	@OneToMany(mappedBy = "jobPost", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<JobPostLocationTag> jobPostLocationTags = new ArrayList<>();

	@OneToMany(mappedBy = "jobPost", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<JobPostGenreTag> jobPostGenreTags = new ArrayList<>();

	@OneToMany(mappedBy = "jobPost", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<JobPostApply> jobApplicant = new ArrayList<>();



	public static JobPost createNewJobPost(
		User userId,
		String title,
		String content,
		LocalDateTime expirationDate,
		Long peopleRecruited,
		Long bookMark,
		IsActive isActive
	) {
		JobPost jobPost = new JobPost();
		jobPost.userId = userId;
		jobPost.title = title;
		jobPost.content = content;
		jobPost.expirationDate = expirationDate;
		jobPost.peopleRecruited = peopleRecruited;
		jobPost.bookMark = bookMark;
		jobPost.isActive = isActive;
		return jobPost;
	}

	public void updateJobPost(UpdateJobPostCommand command) {

		this.title = command.getTitle();
		this.content = command.getContent();
		this.expirationDate = command.getExpirationDate();
		this.peopleRecruited = command.getPeopleRecruited();
		this.bookMark = command.getBookMark();
		this.isActive = command.getIsActive();

	}

	public void updateJobPostInstrumentTags(List<JobPostInstrumentTag> jobPostInstrumentTags) {
		this.jobPostInstrumentTags = jobPostInstrumentTags;
	}

	public void updateJobPostLocationTags(List<JobPostLocationTag> jobPostLocationTags) {
		this.jobPostLocationTags = jobPostLocationTags;
	}
	public void updateJobPostGenreTags(List<JobPostGenreTag> jobPostGenreTags) {
		this.jobPostGenreTags = jobPostGenreTags;
	}

	public void plusApplicant() {
		this.totalApplicant += 1;
	}
	public void minusApplicant() {
		this.totalApplicant -= 1;
	}
	public void plusBookMark() {
		this.bookMark += 1;
	}
	public void minusBookMark() {
		this.bookMark -= 1;
	}

	public void deleteJobPostInstrumentTags() {
		jobPostInstrumentTags.clear();
	}

	public void deleteJobPostLocationTags() {
		jobPostLocationTags.clear();
	}

	public void deleteJobPostGenreTags() {
		jobPostGenreTags.clear();
	}

}
