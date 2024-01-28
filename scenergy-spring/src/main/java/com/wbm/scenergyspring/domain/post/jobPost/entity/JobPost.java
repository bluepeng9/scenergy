package com.wbm.scenergyspring.domain.post.jobPost.entity;

import java.time.LocalDateTime;

import com.wbm.scenergyspring.domain.post.Post;
import com.wbm.scenergyspring.domain.user.entity.User;

import jakarta.persistence.Entity;
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

	Boolean isActive;

	Long peopleRecrutied;

	Long bookMark;

	public static JobPost createNewJobPost(
		User userId,
		String title,
		String content,
		LocalDateTime expirationDate,
		Long peopleRecrutied,
		Long bookMark
	) {
		JobPost jobPost = new JobPost();
		jobPost.userId = userId;
		jobPost.title = title;
		jobPost.content = content;
		jobPost.expirationDate = expirationDate;
		jobPost.isActive = true;
		jobPost.peopleRecrutied = peopleRecrutied;
		jobPost.bookMark = bookMark;
		return jobPost;
	}


}
