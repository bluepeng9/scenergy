package com.wbm.scenergyspring.domain.chat.controller;

import com.wbm.scenergyspring.domain.chat.controller.request.GetMessageInfoRequest;
import com.wbm.scenergyspring.domain.chat.controller.request.GetRoomInfoRequest;
import com.wbm.scenergyspring.domain.chat.controller.response.GetMessageInfoResponse;
import com.wbm.scenergyspring.domain.chat.controller.response.GetRoomInfoResponse;
import com.wbm.scenergyspring.domain.chat.dto.ChatMessageDto;
import com.wbm.scenergyspring.domain.chat.dto.ChatRoomDto;
import com.wbm.scenergyspring.domain.chat.service.ChatService;
import com.wbm.scenergyspring.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class ChatMessageController {

    private final ChatService chatService;

    @GetMapping("/tmp")
    public ResponseEntity<ApiResponse<GetMessageInfoResponse>> getMessageInfo(GetMessageInfoRequest request) {
        log.info("GetMessageInfoRequest: " + request);
        ChatMessageDto chatMessageDto = chatService.getMessageInfo(request.toGetMessageInfoCommand());
        GetMessageInfoResponse getMessageInfoResponse = GetMessageInfoResponse.builder()
                .chatMessageDto(chatMessageDto)
                .build();
        return ResponseEntity.ok(ApiResponse.createSuccess(getMessageInfoResponse));
    }

    @GetMapping("/tmp1")
    public ResponseEntity<ApiResponse<GetRoomInfoResponse>> getRoomInfo(GetRoomInfoRequest request) {
        log.info("GetRoomInfoRequest: " + request);
        ChatRoomDto chatRoomDto = chatService.getRoomInfo(request.toGetRoomInfoCommand());
        GetRoomInfoResponse getRoomInfoResponse = GetRoomInfoResponse.builder()
                .roomInfo(chatRoomDto)
                .build();
        return ResponseEntity.ok(ApiResponse.createSuccess(getRoomInfoResponse));
    }
}