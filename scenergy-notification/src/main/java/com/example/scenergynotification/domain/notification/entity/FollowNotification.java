package com.example.scenergynotification.domain.notification.entity;


import com.example.scenergynotification.domain.user.entity.User;

import jakarta.persistence.Entity;

@Entity
public class FollowNotification extends Notification {

	public static FollowNotification createFollowNotification(
		User receiver,
		User sender
	) {
		FollowNotification followNotification = new FollowNotification();
		followNotification.updateNotificationInfo(
			receiver,
			sender,
			NotificationStatus.UNREAD
		);

		return followNotification;
	}
}