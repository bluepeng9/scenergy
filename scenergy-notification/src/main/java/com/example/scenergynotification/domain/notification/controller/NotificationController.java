package com.example.scenergynotification.domain.notification.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.example.scenergynotification.domain.chat.domain.Chat;
import com.example.scenergynotification.domain.chat.service.ChatService;
import com.example.scenergynotification.domain.chat.service.command.GetChatMessageInfoCommand;
import com.example.scenergynotification.domain.notification.controller.request.OnFollowEvent;
import com.example.scenergynotification.domain.notification.controller.request.OnUnreadMessageEvent;
import com.example.scenergynotification.domain.notification.controller.response.ChatNotificationDTO;
import com.example.scenergynotification.domain.notification.controller.response.GetNotificationResponse;
import com.example.scenergynotification.domain.notification.entity.ChatNotification;
import com.example.scenergynotification.domain.notification.entity.FollowNotification;
import com.example.scenergynotification.domain.notification.entity.Notification;
import com.example.scenergynotification.domain.notification.service.NotificationService;
import com.example.scenergynotification.domain.notification.service.command.SendFollowNotificationCommand;
import com.example.scenergynotification.domain.notification.service.command.SendUnreadChatNotificationCommand;
import com.example.scenergynotification.domain.user.entity.User;
import com.example.scenergynotification.domain.user.service.UserService;
import com.example.scenergynotification.global.SseEmitters;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class NotificationController {

	final NotificationService notificationService;
	final ChatService chatService;
	final UserService userService;
	final SseEmitters sseEmitters;

	@KafkaListener(topics = "follow", groupId = "follow-group", containerFactory = "followEventKafkaListenerContainerFactory")
	public void onFollowEvent(OnFollowEvent event) {
		log.debug("onFollowEvent: {}", event);
		User fromUserInfo = userService.findUser(event.getFromUserId());

		FollowNotification followNotification = notificationService.sendFollowNotification(
			SendFollowNotificationCommand.builder()
				.fromUserId(event.getFromUserId())
				.toUserId(event.getToUserId())
				.fromUserNickname(fromUserInfo.getNickname())
				.build()
		);

		sseEmitters.emit(
			event.getToUserId(),
			ChatNotificationDTO.from(followNotification),
			"notification"
		);
	}

	@GetMapping(path = "/connect/{userId}")
	public ResponseEntity<SseEmitter> connect(@PathVariable("userId") Long userId) {
		SseEmitter emitter = sseEmitters.subscribe(userId);
		return ResponseEntity.ok(emitter);
	}

	@KafkaListener(topics = "unreadChat", groupId = "unread-group", containerFactory = "unreadMessageEventKafkaListenerContainerFactory")
	public void onUnreadChatEvent(OnUnreadMessageEvent event) {
		log.debug("onUnreadChatEvent: {}", event);

		User receiveUser = userService.findUser(event.getUserId());
		log.debug("receiveUser: {}", receiveUser);
		Chat chat = chatService.getChatMessageInfo(
			GetChatMessageInfoCommand.builder().
				chatMessageId(event.getChatMessageId())
				.build()
		);
		log.debug("chat: {}", chat);
		User sender = userService.findUser(chat.getSenderId());
		log.debug("sender: {}", sender);

		SendUnreadChatNotificationCommand command = SendUnreadChatNotificationCommand.builder()
			.receiver(receiveUser.getId())
			.sender(chat.getSenderId())
			.chatMessage(chat.getChatMessage())
			.senderNickname(sender.getNickname())
			.build();
		log.debug("command: {}", command);

		ChatNotification chatNotification = notificationService.sendUnreadChatNotification(command);

		sseEmitters.emit(
			event.getUserId(),
			ChatNotificationDTO.from(chatNotification),
			"notification"
		);

	}

	@GetMapping(path = "/users/{userId}/notification")
	public ResponseEntity<GetNotificationResponse> getNotifications(@PathVariable("userId") Long userId) {
		List<Notification> allNotifications = notificationService.getAllNotifications(userId);
		log.debug("allNotifications: {}", allNotifications);

		GetNotificationResponse from = GetNotificationResponse.from(allNotifications);
		log.debug("from: {}", from);
		return ResponseEntity.ok(from);
	}

}
