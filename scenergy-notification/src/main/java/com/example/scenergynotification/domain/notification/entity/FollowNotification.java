package com.example.scenergynotification.domain.notification.entity;

import jakarta.persistence.Entity;

@Entity
public class FollowNotification extends Notification {

	public static FollowNotification createFollowNotification(
		Long receiver,
		Long sender,
		String senderNickname
	) {
		FollowNotification followNotification = new FollowNotification();
		followNotification.updateNotificationInfo(
			receiver,
			sender,
			NotificationStatus.UNREAD,
			senderNickname
		);

		return followNotification;
	}
}