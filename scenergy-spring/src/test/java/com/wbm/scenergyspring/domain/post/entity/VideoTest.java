package com.wbm.scenergyspring.domain.post.entity;

import com.wbm.scenergyspring.domain.post.repository.VideoRepository;
import com.wbm.scenergyspring.domain.post.service.VideoPostService;
import com.wbm.scenergyspring.domain.post.service.command.VideoCommand;
import com.wbm.scenergyspring.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VideoTest {

    @Autowired
    public VideoRepository videoRepository;
    @Autowired
    public VideoPostService videoPostService;

    @Test
    @Transactional
    @DisplayName("Video 엔티티 생성 테스트")
    void createVideo() {
        //given
        VideoCommand videoCommand = VideoCommand.builder()
                .musicTitle("testMusic")
                .videoUrl("testVideoUrl")
                .artist("testArtist")
                .thumbnail("testThumbnailUrl")
                .build();
        //when
        videoPostService.createVideo(videoCommand);
        Optional<Video> testVideo = videoRepository.findById(1L);

        //then
        Assertions.assertTrue(testVideo.isPresent());
    }
}