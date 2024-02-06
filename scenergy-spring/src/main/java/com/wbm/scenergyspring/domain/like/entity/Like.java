package com.wbm.scenergyspring.domain.like.entity;

import com.wbm.scenergyspring.domain.post.videoPost.entity.VideoPost;
import com.wbm.scenergyspring.domain.user.entity.User;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "likes")
public class Like {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JoinColumn(name = "video_post_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private VideoPost videoPost;

	@JoinColumn(name = "user_id")
	@ManyToOne(fetch = FetchType.LAZY)
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
