package com.wbm.scenergyspring.domain.post.jobPost.entity;

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
public class JobApplicant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	JobPost postId;

	@ManyToOne(fetch = FetchType.LAZY)
	User user;

	public static JobApplicant createJobApplicant(
		JobPost postId,
		User userId
	) {
		JobApplicant jobApplicant = new JobApplicant();
		jobApplicant.postId = postId;
		jobApplicant.user = userId;
		return jobApplicant;
	}

}
