package com.wbm.scenergyspring.domain.notification.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wbm.scenergyspring.domain.notification.entity.FollowNotification;
import com.wbm.scenergyspring.domain.notification.entity.Notification;
import com.wbm.scenergyspring.domain.notification.repository.NotificationRepository;
import com.wbm.scenergyspring.domain.notification.service.command.SendFollowNotificationCommand;
import com.wbm.scenergyspring.domain.user.entity.User;
import com.wbm.scenergyspring.domain.user.repository.UserRepository;
import com.wbm.scenergyspring.global.exception.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationService {

	private final NotificationRepository notificationRepository;
	private final UserRepository userRepository;

	@Transactional(readOnly = false)
	public Long sendFollowNotification(SendFollowNotificationCommand command) {

		User fromUser = userRepository.getReferenceById(command.getFromUserId());
		User toUser = userRepository.getReferenceById(command.getToUserId());

		FollowNotification followNotification = FollowNotification.createFollowNotification(
			toUser,
			fromUser
		);

		return notificationRepository.save(followNotification).getId();
	}

	@Transactional(readOnly = false)
	public void readNotification(Long notificationId) {
		Notification notification = notificationRepository.findById(notificationId).orElseThrow(
			() -> new EntityNotFoundException("notification not found")
		);
		notification.readNotification();
	}
}
