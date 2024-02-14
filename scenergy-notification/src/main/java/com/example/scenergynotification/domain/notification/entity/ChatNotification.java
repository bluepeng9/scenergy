package com.example.scenergynotification.domain.notification.entity;


import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.ToString;

@Entity
@ToString
@Getter
public class ChatNotification extends Notification {

	private String chatMessage;

	public static ChatNotification createChatNotification(
		Long receiver,
		Long sender,
		String chatMessage,
		String senderNickname
	) {
		ChatNotification notification = new ChatNotification();
		notification.chatMessage = chatMessage;
		notification.updateNotificationInfo(
			receiver,
			sender,
			NotificationStatus.UNREAD,
			senderNickname
		);
		return notification;
	}
}