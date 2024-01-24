package com.wbm.scenergyspring.domain.like;

import com.wbm.scenergyspring.domain.post.entity.VideoPost;
import com.wbm.scenergyspring.domain.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Like {

	@Id
	private Long id;

	@Column(name = "video_post_id")
	@OneToOne(fetch = FetchType.LAZY)
	private VideoPost videoPost;

	@Column(name = "user_id")
	@OneToOne(fetch = FetchType.LAZY)
	private User user;

	public static Like createNewLike(
		VideoPost videoPost,
		User user
	) {
		Like like = new Like();
		like.videoPost = videoPost;
		like.user = user;
		return like;
	}
}
