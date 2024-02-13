package com.example.scenergynotification.domain.chat.domain;

import lombok.Data;

@Data
public class Chat {

	String chatMessage;
	private Long id;
	private Long senderId;

}
