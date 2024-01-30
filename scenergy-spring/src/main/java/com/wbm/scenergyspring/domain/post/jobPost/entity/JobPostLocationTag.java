package com.wbm.scenergyspring.domain.post.jobPost.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.wbm.scenergyspring.domain.tag.entity.LocationTag;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class JobPostLocationTag {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private JobPost jobPost;

	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private LocationTag locationTag;

	public void updateJobPost(JobPost jobPost) {
		this.jobPost = jobPost;
	}

	public void updateLocationTag(LocationTag locationTag) {
		this.locationTag = locationTag;
	}
}
