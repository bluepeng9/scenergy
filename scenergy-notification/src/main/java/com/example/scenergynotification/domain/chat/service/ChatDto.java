package com.example.scenergynotification.domain.chat.service;

import com.example.scenergynotification.domain.chat.domain.Chat;

import lombok.Data;

@Data
public class ChatDto {

	ChatMessageDto data;

	public Chat toChat() {
		Chat chat = new Chat();
		chat.setChatMessage(data.getChatMessage());
		chat.setId(data.getId());
		chat.setSenderId(data.getSenderId());
		return chat;
	}

}

@Data
class ChatMessageDto {

	String chatMessage;
	Long id;
	Long senderId;
}
