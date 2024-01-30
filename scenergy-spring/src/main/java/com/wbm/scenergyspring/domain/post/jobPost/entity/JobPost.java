package com.wbm.scenergyspring.domain.post.jobPost.entity;

import java.time.LocalDateTime;

import com.wbm.scenergyspring.domain.post.Post;
import com.wbm.scenergyspring.domain.user.entity.User;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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

	Long peopleRecrutied;

	Long bookMark;

	public static JobPost createNewJobPost(
		User userId,
		String title,
		String content,
		LocalDateTime expirationDate,
		Long peopleRecrutied,
		Long bookMark,
		IsActive isActive
	) {
		JobPost jobPost = new JobPost();
		jobPost.userId = userId;
		jobPost.title = title;
		jobPost.content = content;
		jobPost.expirationDate = expirationDate;
		jobPost.peopleRecrutied = peopleRecrutied;
		jobPost.bookMark = bookMark;
		jobPost.isActive = isActive;
		return jobPost;
	}

	public void updateJobPost(
		Long jobPostId,
		String title,
		String content,
		LocalDateTime expirationDate,
		Long peopleRecrutied,
		Long bookMark,
		IsActive isActive
	) {
		this.title = title;
		this.content = content;
		this.expirationDate = expirationDate;
		this.peopleRecrutied = peopleRecrutied;
		this.bookMark = bookMark;
		this.isActive = isActive;
	}

}
