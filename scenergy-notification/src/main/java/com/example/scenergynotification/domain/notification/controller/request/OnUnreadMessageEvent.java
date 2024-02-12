package com.example.scenergynotification.domain.notification.controller.request;

import lombok.Data;

@Data
public class OnUnreadMessageEvent {

	private Long id;
	private Long chatRoomId;
	private Long userId;
	private Long chatMessageId;
}
