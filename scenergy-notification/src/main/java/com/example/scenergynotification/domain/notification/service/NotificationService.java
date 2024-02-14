package com.example.scenergynotification.domain.notification.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.scenergynotification.domain.notification.entity.ChatNotification;
import com.example.scenergynotification.domain.notification.entity.FollowNotification;
import com.example.scenergynotification.domain.notification.entity.Notification;
import com.example.scenergynotification.domain.notification.repository.NotificationRepository;
import com.example.scenergynotification.domain.notification.service.command.SendFollowNotificationCommand;
import com.example.scenergynotification.domain.notification.service.command.SendUnreadChatNotificationCommand;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationService {

	private final NotificationRepository notificationRepository;

	@Transactional(readOnly = false)
	public FollowNotification sendFollowNotification(SendFollowNotificationCommand command) {
		FollowNotification notification = FollowNotification.createFollowNotification(
			command.getReceiver(),
			command.getSender(),
			command.getFromUserNickname()
		);
		FollowNotification save = notificationRepository.save(notification);
		return save;
	}

	@Transactional(readOnly = false)
	public void readNotification(Long notificationId) {
		Notification notification = notificationRepository.findById(notificationId).orElseThrow(
			() -> new EntityNotFoundException("notification not found")
		);
		notification.readNotification();
	}

	public ChatNotification sendUnreadChatNotification(SendUnreadChatNotificationCommand command) {
		ChatNotification chatNotification = ChatNotification.createChatNotification(
			command.getReceiver(),
			command.getSender(),
			command.getChatMessage(),
			command.getSenderNickname(),
			command.getChatRoomId()
		);
		return notificationRepository.save(chatNotification);
	}

	public List<Notification> getAllNotifications(Long userId) {
		return notificationRepository.findAllByReceiver(userId);
	}
}
