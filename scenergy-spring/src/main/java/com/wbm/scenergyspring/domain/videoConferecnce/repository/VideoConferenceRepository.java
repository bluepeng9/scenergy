package com.wbm.scenergyspring.domain.videoConferecnce.repository;

import com.wbm.scenergyspring.domain.videoConferecnce.entity.VideoConference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VideoConferenceRepository extends JpaRepository<VideoConference, Long> {

    Optional<VideoConference> findVideoConferenceByChatRoomIdAndUserId(Long chatRoomId, Long userId);

}
