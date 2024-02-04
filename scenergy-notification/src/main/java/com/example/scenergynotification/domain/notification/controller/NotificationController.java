package com.example.scenergynotification.domain.notification.controller;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;

import com.example.scenergynotification.domain.notification.controller.request.OnFollowRequest;
import com.example.scenergynotification.domain.notification.controller.request.OnLikeRequest;
import com.example.scenergynotification.domain.notification.service.NotificationService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class NotificationController {

	final NotificationService notificationService;

	@KafkaListener(topics = "follow", groupId = "follow-group", containerFactory = "followEventKafkaListenerContainerFactory")
	public void onFollowEvent(OnFollowRequest request) {
		System.out.println(request.getFollowId());
	}

	@KafkaListener(topics = "like", groupId = "like-group", containerFactory = "likeEventKafkaListenerContainerFactory")
	public void onLikeEvent(OnLikeRequest request) {
		System.out.println(request);
	}
}
