package com.example.scenergynotification.domain.notification.controller.response;

import com.example.scenergynotification.domain.notification.entity.ChatNotification;
import com.example.scenergynotification.domain.notification.entity.FollowNotification;
import com.example.scenergynotification.domain.notification.entity.LikeNotification;
import com.example.scenergynotification.domain.notification.entity.Notification;
import com.example.scenergynotification.domain.notification.entity.NotificationStatus;

import lombok.Data;

@Data
public abstract class NotificationDTO {
	private Long id;
	private Long receiver;
	private Long sender;
	private String senderNickname;
	private NotificationStatus status;

	public static NotificationDTO from(Notification notification) {
		if (notification instanceof FollowNotification) {
			return FollowNotificationDTO.from((FollowNotification)notification);
		} else if (notification instanceof ChatNotification) {
			return ChatNotificationDTO.from((ChatNotification)notification);
		} else if (notification instanceof LikeNotification) {
			return LikeNotificationDTO.from((LikeNotification)notification);
		}
		throw new IllegalArgumentException("Unknown notification type");
	}

	void update(Notification notification) {
		this.id = notification.getId();
		this.receiver = notification.getReceiver();
		this.sender = notification.getSender();
		this.senderNickname = notification.getSenderNickname();
		this.status = notification.getStatus();
	}

}
