package com.wbm.scenergyspring.domain.videoConferecnce.controller;

import com.wbm.scenergyspring.domain.videoConferecnce.controller.request.GetPeerIdRequest;
import com.wbm.scenergyspring.domain.videoConferecnce.controller.request.SavePeerIdRequest;
import com.wbm.scenergyspring.domain.videoConferecnce.service.VideoConferenceService;
import com.wbm.scenergyspring.domain.videoConferecnce.service.command.GetPeerIdCommand;
import com.wbm.scenergyspring.domain.videoConferecnce.service.command.SavePeerIdCommand;
import com.wbm.scenergyspring.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/video-conference")
public class VideoConferenceController {

    private final VideoConferenceService videoConferenceService;

    @PostMapping("/save/peerId")
    public ResponseEntity<ApiResponse<String>> savePeerId(@RequestBody SavePeerIdRequest request) {
        SavePeerIdCommand command = SavePeerIdCommand.builder()
                .chatRoomId(request.getChatRoomId())
                .userId(request.getUserId())
                .peerId(request.getPeerId())
                .build();
        String response = videoConferenceService.savePeerId(command);
        return ResponseEntity.ok(ApiResponse.createSuccess(response));
    }

    @PostMapping("/get/peerId")
    public ResponseEntity<ApiResponse<String>> getPeerId(@RequestBody GetPeerIdRequest request) {
        GetPeerIdCommand command = GetPeerIdCommand.builder()
                .chatRoomId(request.getChatRoomId())
                .userId(request.getUserId())
                .build();
        String response = videoConferenceService.getPeerId(command);
        return ResponseEntity.ok(ApiResponse.createSuccess(response));
    }
}
