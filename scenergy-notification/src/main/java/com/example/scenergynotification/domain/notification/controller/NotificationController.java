package com.example.scenergynotification.domain.notification.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.example.scenergynotification.domain.notification.controller.request.OnFollowEvent;
import com.example.scenergynotification.domain.notification.controller.request.OnLikeEvent;
import com.example.scenergynotification.domain.notification.controller.response.NotificationTypeResponse;
import com.example.scenergynotification.domain.notification.controller.response.SseNotificationResponse;
import com.example.scenergynotification.domain.notification.service.NotificationService;
import com.example.scenergynotification.domain.notification.service.command.SendFollowNotificationCommand;
import com.example.scenergynotification.domain.notification.service.commandresult.SendFollowNotificationCommandResult;
import com.example.scenergynotification.domain.user.entity.User;
import com.example.scenergynotification.domain.user.service.UserService;
import com.example.scenergynotification.global.SseEmitters;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class NotificationController {

	final NotificationService notificationService;
	final UserService userService;
	final SseEmitters sseEmitters;

	@KafkaListener(topics = "follow", groupId = "follow-group", containerFactory = "followEventKafkaListenerContainerFactory")
	public void onFollowEvent(OnFollowEvent event) {
		User fromUserInfo = userService.findUser(event.getFromUserId());

		SendFollowNotificationCommandResult sendFollowNotificationCommandResult = notificationService.sendFollowNotification(
			SendFollowNotificationCommand.builder()
				.fromUserId(event.getFromUserId())
				.toUserId(event.getToUserId())
				.build()
		);

		sseEmitters.emit(
			event.getToUserId(),
			SseNotificationResponse.builder()
				.notificationId(sendFollowNotificationCommandResult.getNotificationId())
				.senderNickname(fromUserInfo.getNickname())
				.notificationTypeResponse(NotificationTypeResponse.FOLLOW)
				.build(),
			"notification"
		);
	}

	@GetMapping(path = "/connect/{userId}")
	public ResponseEntity<SseEmitter> connect(@PathVariable("userId") Long userId) {
		SseEmitter emitter = sseEmitters.subscribe(userId);
		return ResponseEntity.ok(emitter);
	}

	@KafkaListener(topics = "like", groupId = "like-group", containerFactory = "likeEventKafkaListenerContainerFactory")
	public void onLikeEvent(OnLikeEvent event) {
		System.out.println(event);
	}

}
