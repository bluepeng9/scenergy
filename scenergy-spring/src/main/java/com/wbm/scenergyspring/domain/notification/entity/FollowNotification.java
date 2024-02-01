package com.wbm.scenergyspring.domain.notification.entity;

import com.wbm.scenergyspring.domain.user.entity.User;

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