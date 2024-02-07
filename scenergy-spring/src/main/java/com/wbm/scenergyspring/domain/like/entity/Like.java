package com.wbm.scenergyspring.domain.like.entity;

import com.wbm.scenergyspring.domain.post.videoPost.entity.VideoPost;
import com.wbm.scenergyspring.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
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
