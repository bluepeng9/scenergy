package com.wbm.scenergyspring.domain.post.entity;

import com.wbm.scenergyspring.domain.post.repository.VideoRepository;
import com.wbm.scenergyspring.domain.post.service.VideoPostService;
import com.wbm.scenergyspring.domain.post.service.command.CreateVideoCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

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
        CreateVideoCommand createVideoCommand = CreateVideoCommand.builder()
                .videoTitle("testMusic")
                .videoUrlPath("testVideoUrl")
                .artist("testArtist")
                .thumbnailUrlPath("testThumbnailUrl")
                .build();
        //when
        Video video = videoPostService.createVideo(createVideoCommand);
        Optional<Video> testVideo = videoRepository.findById(1L);

        //then
        assertThat(testVideo).isPresent();
        assertThat(testVideo.get().getVideoUrlPath()).isEqualTo(video.getVideoUrlPath());
        assertThat(testVideo.get().getArtist()).isEqualTo(video.getArtist());
        assertThat(testVideo.get().getThumbnailUrlPath()).isEqualTo(video.getThumbnailUrlPath());
        assertThat(testVideo.get().getMusicTitle()).isEqualTo(video.getMusicTitle());
    }
}