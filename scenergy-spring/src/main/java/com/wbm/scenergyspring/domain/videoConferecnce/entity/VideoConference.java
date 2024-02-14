package com.wbm.scenergyspring.domain.videoConferecnce.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class VideoConference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Long chatRoomId;
    Long userId;
    String peerId;

    public static VideoConference createVideoConference(
            Long chatRoomId,
            Long userId,
            String peerId
    ) {
        VideoConference videoConference = new VideoConference();
        videoConference.chatRoomId = chatRoomId;
        videoConference.userId = userId;
        videoConference.peerId = peerId;

        return videoConference;
    }

    public void updatePeerId(String peerId) {
        this.peerId = peerId;
    }

}
