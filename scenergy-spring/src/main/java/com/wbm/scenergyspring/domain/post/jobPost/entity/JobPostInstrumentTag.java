package com.wbm.scenergyspring.domain.post.jobPost.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.wbm.scenergyspring.domain.tag.entity.InstrumentTag;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class JobPostInstrumentTag {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private JobPost jobPost;

	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private InstrumentTag instrumentTag;

	public void updateJobPost(JobPost jobPost) {
		this.jobPost = jobPost;
	}

	public void updateInstrumentTag(InstrumentTag instrumentTag) {
		this.instrumentTag = instrumentTag;
	}
}
