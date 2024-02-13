package com.wbm.scenergyspring.domain.chat.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wbm.scenergyspring.domain.chat.controller.response.GetMessageInfoResponse;
import com.wbm.scenergyspring.domain.chat.controller.response.GetRoomInfoResponse;
import com.wbm.scenergyspring.domain.chat.dto.ChatMessageDto;
import com.wbm.scenergyspring.domain.chat.dto.ChatRoomDto;
import com.wbm.scenergyspring.domain.chat.service.ChatService;
import com.wbm.scenergyspring.domain.chat.service.command.GetMessageInfoCommand;
import com.wbm.scenergyspring.domain.chat.service.command.GetRoomInfoCommand;
import com.wbm.scenergyspring.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/chatMessage")
public class ChatMessageController {

	private final ChatService chatService;

	@GetMapping("/{chatMessageId}")
	public ResponseEntity<ApiResponse<GetMessageInfoResponse>> getMessageInfo(
		@PathVariable("chatMessageId") Long chatMessageId
	) {
		log.info("GetMessageInfoRequest: " + chatMessageId);
		ChatMessageDto chatMessageDto = chatService.getMessageInfo(
			GetMessageInfoCommand.builder()
				.MessageId(chatMessageId)
				.build()
		);
		GetMessageInfoResponse getMessageInfoResponse = GetMessageInfoResponse.builder()
			.senderId(chatMessageDto.getSenderId())
			.chatMessage(chatMessageDto.getMessageText())
			.build();
		return ResponseEntity.ok(ApiResponse.createSuccess(getMessageInfoResponse));
	}

	@GetMapping("/room/{roomId}")
	public ResponseEntity<ApiResponse<GetRoomInfoResponse>> getRoomInfo(
		@PathVariable("roomId") Long roomId
	) {
		log.info("GetRoomInfoRequest: " + roomId);
		ChatRoomDto chatRoomDto = chatService.getRoomInfo(GetRoomInfoCommand.builder()
			.RoomId(roomId)
			.build());
		GetRoomInfoResponse getRoomInfoResponse = GetRoomInfoResponse.builder()
			.roomInfo(chatRoomDto)
			.build();
		return ResponseEntity.ok(ApiResponse.createSuccess(getRoomInfoResponse));
	}
}