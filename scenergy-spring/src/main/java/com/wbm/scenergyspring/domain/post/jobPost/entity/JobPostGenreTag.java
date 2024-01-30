package com.wbm.scenergyspring.domain.post.jobPost.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.wbm.scenergyspring.domain.tag.entity.GenreTag;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class JobPostGenreTag {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name="job_post_id")
	private JobPost jobPost;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "genre_tag_id")
	private GenreTag genreTag;

	public void updateJobPost(JobPost jobPost) {
		this.jobPost = jobPost;
	}

	public void updateGenreTag(GenreTag genreTag) {
		this.genreTag = genreTag;
	}

}
