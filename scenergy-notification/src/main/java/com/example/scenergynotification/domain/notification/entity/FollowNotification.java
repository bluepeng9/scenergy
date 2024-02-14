package com.example.scenergynotification.domain.notification.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.ToString;

@Entity
@Getter
@ToString
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