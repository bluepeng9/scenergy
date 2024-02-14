package com.example.scenergynotification.domain.notification.controller.response;

import com.example.scenergynotification.domain.notification.entity.FollowNotification;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FollowNotificationDTO extends NotificationDTO {

	public static FollowNotificationDTO from(FollowNotification notification) {
		FollowNotificationDTO dto = FollowNotificationDTO.builder()
			.build();
		dto.update(notification);
		return dto;
	}

}
