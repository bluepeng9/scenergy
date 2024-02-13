package com.example.scenergynotification.domain.notification.entity;

import jakarta.persistence.Entity;

@Entity
public class ChatNotification extends Notification {

	private String chatMessage;

	public static ChatNotification createChatNotification(
		Long receiver,
		Long sender,
		String chatMessage
	) {
		ChatNotification notification = new ChatNotification();
		notification.chatMessage = chatMessage;
		notification.updateNotificationInfo(receiver, sender, NotificationStatus.UNREAD);
		return notification;
	}
}