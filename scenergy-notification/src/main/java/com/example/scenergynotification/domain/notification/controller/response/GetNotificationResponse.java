package com.example.scenergynotification.domain.notification.controller.response;

import java.util.List;

import com.example.scenergynotification.domain.notification.entity.Notification;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetNotificationResponse {
	List<NotificationDTO> notifications;

	public static GetNotificationResponse from(List<Notification> notifications) {
		return GetNotificationResponse.builder()
			.notifications(
				notifications.stream()
					.map(NotificationDTO::from)
					.toList()
			)
			.build();
	}
}

