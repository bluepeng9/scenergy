package com.example.scenergynotification.domain.notification.controller.response;

import com.example.scenergynotification.domain.notification.entity.ChatNotification;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ChatNotificationDTO extends NotificationDTO {
	private String chatMessage;
	private Long roomId;

	public static ChatNotificationDTO from(ChatNotification notification) {
		ChatNotificationDTO dto = ChatNotificationDTO.builder()
			.chatMessage(notification.getChatMessage())
			.roomId(notification.getRoomId())
			.build();
		dto.update(notification);
		return dto;
	}
}
