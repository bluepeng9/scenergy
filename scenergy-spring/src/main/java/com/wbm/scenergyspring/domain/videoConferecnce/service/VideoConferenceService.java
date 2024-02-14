package com.wbm.scenergyspring.domain.videoConferecnce.service;

import com.wbm.scenergyspring.domain.videoConferecnce.entity.VideoConference;
import com.wbm.scenergyspring.domain.videoConferecnce.repository.VideoConferenceRepository;
import com.wbm.scenergyspring.domain.videoConferecnce.service.command.GetPeerIdCommand;
import com.wbm.scenergyspring.domain.videoConferecnce.service.command.SavePeerIdCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VideoConferenceService {

    private final VideoConferenceRepository videoConferenceRepository;

    @Transactional(readOnly = false)
    public String savePeerId(SavePeerIdCommand command) {
        try {
            VideoConference videoConference = videoConferenceRepository.findVideoConferenceByChatRoomIdAndUserId(command.getChatRoomId(), command.getUserId()).get();
            videoConference.updatePeerId(command.getPeerId());
        } catch (Exception e) {
            VideoConference videoConference = VideoConference.createVideoConference(command.getChatRoomId(), command.getUserId(), command.getPeerId());
            videoConferenceRepository.save(videoConference);
        }
        return "success";
    }

    public String getPeerId(GetPeerIdCommand command) {
        String result = "";
        try {
            VideoConference videoConference = videoConferenceRepository.findVideoConferenceByChatRoomIdAndUserId(command.getChatRoomId(), command.getUserId()).get();
            result = videoConference.getPeerId();
        } catch (Exception e) {
            result = "false";
        }
        return result;
    }

}
