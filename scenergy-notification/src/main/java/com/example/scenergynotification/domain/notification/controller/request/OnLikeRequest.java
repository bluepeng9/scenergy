package com.example.scenergynotification.domain.notification.controller.request;

import lombok.Data;

@Data
public class OnLikeRequest {
	private Long likeId;
	private Long fromUserId;
	private Long toUserId;
	private Long postId;
}
