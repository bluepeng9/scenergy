package com.example.scenergynotification.domain.notification.service.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SendUnreadChatNotificationCommand {

	private Long receiver;
	private Long sender;
	private String chatMessage;
	private String senderNickname;
}
