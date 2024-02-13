package com.example.scenergynotification.domain.chat.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.scenergynotification.domain.chat.domain.Chat;
import com.example.scenergynotification.domain.chat.service.command.GetChatMessageInfoCommand;

@Service
public class ChatService {

	@Value("${spring.user-service.url}")
	private String userServiceUrl;

	public Chat getChatMessageInfo(GetChatMessageInfoCommand command) {

		return new RestTemplate().getForObject(
			userServiceUrl + "/chatMessage/chatMessage/" + command.getChatMessageId(),
			Chat.class
		);
	}

}
