package com.wbm.scenergyspring.domain.post.entity;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.wbm.scenergyspring.IntegrationTest;
import com.wbm.scenergyspring.domain.post.videoPost.entity.Video;
import com.wbm.scenergyspring.domain.post.videoPost.repository.VideoRepository;
import com.wbm.scenergyspring.domain.post.videoPost.service.VideoPostService;
import com.wbm.scenergyspring.domain.post.videoPost.service.command.CreateVideoCommand;

@SpringBootTest
@Transactional
class VideoTest extends IntegrationTest {

    @Autowired
    public VideoRepository videoRepository;
    @Autowired
    public VideoPostService videoPostService;

    @Test
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
        Optional<Video> testVideo = videoRepository.findById(video.getId());

        //then
        assertThat(testVideo).isPresent();
        assertThat(testVideo.get().getVideoUrlPath()).isEqualTo(video.getVideoUrlPath());
        assertThat(testVideo.get().getArtist()).isEqualTo(video.getArtist());
        assertThat(testVideo.get().getThumbnailUrlPath()).isEqualTo(video.getThumbnailUrlPath());
        assertThat(testVideo.get().getMusicTitle()).isEqualTo(video.getMusicTitle());
    }
}