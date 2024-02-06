package com.wbm.scenergyspring.domain.post.jobPost.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.wbm.scenergyspring.domain.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class JobPostApply {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "job_post_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	JobPost jobPost;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	User user;

	/**
	 * true : 지원
	 * false : 지원 x
	 */
	@Column(nullable = false)
	boolean status;

	public JobPostApply(JobPost jobPost, User user) {
		this.jobPost = jobPost;
		this.user = user;
		this.status = true;
	}

}
