package com.example.scenergynotification.domain.notification.controller.response;

import com.example.scenergynotification.domain.notification.entity.LikeNotification;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LikeNotificationDTO extends NotificationDTO {
	private Long postId;

	public static LikeNotificationDTO from(LikeNotification notification) {
		LikeNotificationDTO dto = LikeNotificationDTO.builder()
			.postId(notification.getPostId())
			.build();
		dto.update(notification);
		return dto;
	}
}
