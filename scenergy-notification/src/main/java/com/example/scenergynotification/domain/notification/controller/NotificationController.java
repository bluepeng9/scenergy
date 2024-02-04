package com.example.scenergynotification.domain.notification.controller;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;

import com.example.scenergynotification.domain.notification.controller.request.OnFollowEvent;
import com.example.scenergynotification.domain.notification.controller.request.OnLikeEvent;
import com.example.scenergynotification.domain.notification.service.NotificationService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class NotificationController {

	final NotificationService notificationService;

	@KafkaListener(topics = "follow", groupId = "follow-group", containerFactory = "followEventKafkaListenerContainerFactory")
	public void onFollowEvent(OnFollowEvent event) {
		System.out.println(event);
	}

	@KafkaListener(topics = "like", groupId = "like-group", containerFactory = "likeEventKafkaListenerContainerFactory")
	public void onLikeEvent(OnLikeEvent event) {
		System.out.println(event);
	}
}
