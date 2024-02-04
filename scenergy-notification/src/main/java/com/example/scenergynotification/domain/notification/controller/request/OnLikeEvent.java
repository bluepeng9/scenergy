package com.example.scenergynotification.domain.notification.controller.request;

import lombok.Data;

@Data
public class OnLikeEvent {
	private Long likeId;
	private Long fromUserId;
	private Long toUserId;
	private Long postId;
}
