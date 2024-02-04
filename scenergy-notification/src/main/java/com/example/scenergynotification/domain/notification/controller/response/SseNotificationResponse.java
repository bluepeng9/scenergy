package com.example.scenergynotification.domain.notification.controller.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SseNotificationResponse {
	Long notificationId;
	String senderNickname;
	NotificationTypeResponse notificationTypeResponse;
}
