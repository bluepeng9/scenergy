package com.wbm.scenergyspring.domain.chat.controller;

import com.wbm.scenergyspring.domain.chat.controller.request.CreateChatRoomRequest;
import com.wbm.scenergyspring.domain.chat.controller.request.InviteChatRoomRequest;
import com.wbm.scenergyspring.domain.chat.controller.request.ListMyChatRoomRequest;
import com.wbm.scenergyspring.domain.chat.controller.request.RenameChatRoomRequest;
import com.wbm.scenergyspring.domain.chat.controller.response.CreateChatRoomResponse;
import com.wbm.scenergyspring.domain.chat.controller.response.InviteChatRoomResponse;
import com.wbm.scenergyspring.domain.chat.controller.response.ListMyChatRoomResponse;
import com.wbm.scenergyspring.domain.chat.controller.response.RenameChatRoomResponse;
import com.wbm.scenergyspring.domain.chat.entity.ChatRoom;
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
@RequestMapping("/chatroom")
public class ChatRoomController {

    private final ChatService chatService;

    @PostMapping("/create-room")
    public ResponseEntity<ApiResponse<CreateChatRoomResponse>> createChatRoom(CreateChatRoomRequest request) {
        log.info("CreateChatRoomRequest: " + request);
        Long chatRoomId = chatService.createChatRoom(request.toCreateChatRoomCommand());
        CreateChatRoomResponse response = CreateChatRoomResponse.builder()
                .chatRoomId(chatRoomId)
                .build();
        return ResponseEntity.ok(ApiResponse.createSuccess(response));
    }

    @PutMapping("/rename-room")
    public ResponseEntity<ApiResponse<RenameChatRoomResponse>> renameChatRoom(RenameChatRoomRequest request) {
        log.info("RenameChatRoomRequest: " + request);
        Long roomId = chatService.renameChatRoom(request.toRenameChatRoomCommand());
        RenameChatRoomResponse response = RenameChatRoomResponse.builder()
                .chatRoomId(roomId)
                .build();
        return ResponseEntity.ok(ApiResponse.createSuccess(response));
    }

    @PostMapping("/invite-room")
    public ResponseEntity<ApiResponse<InviteChatRoomResponse>> inviteChatRoom(InviteChatRoomRequest request) {
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
        List<ChatRoom> list = chatService.listMyChatRoom(request.toListMyChatRoomCommand());
        ListMyChatRoomResponse response = ListMyChatRoomResponse.builder()
                .MyChatRoomList(list)
                .build();
        return ResponseEntity.ok(ApiResponse.createSuccess(response));
    }
}
