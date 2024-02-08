package com.wbm.scenergyspring.domain.post.entity;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.wbm.scenergyspring.IntegrationTest;
import com.wbm.scenergyspring.domain.post.videoPost.entity.Video;
import com.wbm.scenergyspring.domain.post.videoPost.entity.VideoPost;
import com.wbm.scenergyspring.domain.post.videoPost.repository.VideoPostRepository;
import com.wbm.scenergyspring.domain.post.videoPost.repository.VideoRepository;
import com.wbm.scenergyspring.domain.post.videoPost.service.VideoPostService;
import com.wbm.scenergyspring.domain.post.videoPost.service.command.CreateVideoCommand;
import com.wbm.scenergyspring.domain.post.videoPost.service.command.VideoPostCommand;
import com.wbm.scenergyspring.domain.user.service.UserService;
import com.wbm.scenergyspring.domain.user.service.command.CreateUserCommand;

@SpringBootTest
@Transactional
class VideoPostTest extends IntegrationTest {

	@Autowired
	public VideoPostRepository videoPostRepository;
	@Autowired
	public VideoPostService videoPostService;
	@Autowired
	public VideoRepository videoRepository;
	@Autowired
	public UserService userService;

    @Test
    @DisplayName("VideoPost 엔티티 생성 테스트")
    void createVideoPost() {
        //given
        CreateUserCommand command = CreateUserCommand.builder()
                .email("test@gmail.com")
                .password("asdf")
                .build();
        Long userId = userService.createUser(command);

        CreateVideoCommand createVideoCommand = CreateVideoCommand.builder()
                .videoTitle("testMusic")
                .videoUrlPath("testVideoUrl")
                .artist("testArtist")
                .thumbnailUrlPath("testThumbnailUrl")
                .build();
        Video testVideo = videoPostService.createVideo(createVideoCommand);
        List<Long> genreTagIds = new ArrayList<>();
        List<Long> instrumentTagIds = new ArrayList<>();
        VideoPostCommand videoPostCommand = VideoPostCommand.builder()
                .video(testVideo)
                .title("testTitle")
                .content("testContent")
                .genreTagIds(genreTagIds)
                .instrumentTagIds(instrumentTagIds)
                .userId(userId)
                .build();
        //when
        VideoPost videoPost = videoPostService.createVideoPost(videoPostCommand);
        Optional<VideoPost> testVideoPost = videoPostRepository.findById(videoPost.getId());
        //then
        assertThat(testVideoPost).isPresent();
        assertThat(testVideoPost.get().getId()).isEqualTo(videoPost.getId());
        assertThat(testVideoPost.get().getVideo()).isEqualTo(videoPost.getVideo());
        assertThat(testVideoPost.get().getContent()).isEqualTo(videoPost.getContent());
        assertThat(testVideoPost.get().getWriter()).isEqualTo(videoPost.getWriter());
        assertThat(testVideoPost.get().getTitle()).isEqualTo(videoPost.getTitle());
        assertThat(testVideoPost.get().getUser()).isEqualTo(videoPost.getUser());
        assertThat(testVideoPost.get().getVideoPostGenreTags()).isEqualTo(videoPost.getVideoPostGenreTags());

	}
}