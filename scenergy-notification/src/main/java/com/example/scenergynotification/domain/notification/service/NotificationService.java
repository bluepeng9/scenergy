package com.example.scenergynotification.domain.notification.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.scenergynotification.domain.notification.entity.ChatNotification;
import com.example.scenergynotification.domain.notification.entity.FollowNotification;
import com.example.scenergynotification.domain.notification.entity.Notification;
import com.example.scenergynotification.domain.notification.repository.NotificationRepository;
import com.example.scenergynotification.domain.notification.service.command.SendFollowNotificationCommand;
import com.example.scenergynotification.domain.notification.service.command.SendUnreadChatNotificationCommand;
import com.example.scenergynotification.domain.notification.service.commandresult.SendFollowNotificationCommandResult;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationService {

	private final NotificationRepository notificationRepository;

	@Transactional(readOnly = false)
	public SendFollowNotificationCommandResult sendFollowNotification(SendFollowNotificationCommand command) {
		FollowNotification notification = FollowNotification.createFollowNotification(
			command.getFromUserId(),
			command.getToUserId()
		);
		FollowNotification save = notificationRepository.save(notification);
		return SendFollowNotificationCommandResult.builder()
			.notificationId(save.getId())
			.fromUserId(save.getSender())
			.toUserId(save.getReceiver())
			.build();
	}

	@Transactional(readOnly = false)
	public void readNotification(Long notificationId) {
		Notification notification = notificationRepository.findById(notificationId).orElseThrow(
			() -> new EntityNotFoundException("notification not found")
		);
		notification.readNotification();
	}

	public void sendUnreadChatNotification(SendUnreadChatNotificationCommand command) {
		ChatNotification.createChatNotification(
			command.getReceiver(),
			command.getSender(),
			command.getChatMessage()
		);
	}
}
