package com.wbm.scenergyspring.domain.chat.controller;

import com.wbm.scenergyspring.domain.chat.controller.request.*;
import com.wbm.scenergyspring.domain.chat.controller.response.*;
import com.wbm.scenergyspring.domain.chat.dto.ChatMessageDto;
import com.wbm.scenergyspring.domain.chat.dto.ChatRoomDto;
import com.wbm.scenergyspring.domain.chat.service.ChatService;
import com.wbm.scenergyspring.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/chatroom")
public class ChatRoomController {

    private final ChatService chatService;

    @PostMapping("/create-room")
    public ResponseEntity<ApiResponse<CreateChatRoomResponse>> createChatRoom(@RequestBody CreateChatRoomRequest request) {
        log.info("CreateChatRoomRequest: " + request);
        Long chatRoomId = chatService.createChatRoom(request.toCreateChatRoomCommand());
        CreateChatRoomResponse response = CreateChatRoomResponse.builder()
                .chatRoomId(chatRoomId)
                .build();
        return ResponseEntity.ok(ApiResponse.createSuccess(response));
    }

    @PutMapping("/rename-room")
    public ResponseEntity<ApiResponse<RenameChatRoomResponse>> renameChatRoom(@RequestBody RenameChatRoomRequest request) {
        log.info("RenameChatRoomRequest: " + request);
        Long roomId = chatService.renameChatRoom(request.toRenameChatRoomCommand());
        RenameChatRoomResponse response = RenameChatRoomResponse.builder()
                .chatRoomId(roomId)
                .build();
        return ResponseEntity.ok(ApiResponse.createSuccess(response));
    }

    @PostMapping("/invite-room")
    public ResponseEntity<ApiResponse<InviteChatRoomResponse>> inviteChatRoom(@RequestBody InviteChatRoomRequest request) {
        log.info("InviteChatRoomRequest: " + request);
        Long roomId = chatService.inviteChatRoom(request.toInviteChatRoomCommand());
        InviteChatRoomResponse response = InviteChatRoomResponse.builder()
                .chatRoomId(roomId)
                .build();
        return ResponseEntity.ok(ApiResponse.createSuccess(response));
    }

    @GetMapping("/list-mychatroom")
    public ResponseEntity<ApiResponse<ListMyChatRoomResponse>> listMyChatRoom(ListMyChatRoomRequest request) {
        log.info("ListMyChatRoomRequest: " + request);
        List<ChatRoomDto> list = chatService.listMyChatRoom(request.toListMyChatRoomCommand());
        ListMyChatRoomResponse response = ListMyChatRoomResponse.builder()
                .myChatRoomList(list)
                .build();
        return ResponseEntity.ok(ApiResponse.createSuccess(response));
    }

    @GetMapping("/load-message-room")
    public ResponseEntity<ApiResponse<LoadChatMessageResponse>> loadChatMessage(LoadChatMessageRequest request) {
        log.info("LoadChatMessageRequest: " + request);
        List<ChatMessageDto> messageList = chatService.loadChatMessage(request.toLoadChatMessageCommand());
        LoadChatMessageResponse response = LoadChatMessageResponse.builder()
                .messageList(messageList)
                .build();
        return ResponseEntity.ok(ApiResponse.createSuccess(response));
    }
}
