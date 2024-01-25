package com.wbm.scenergyspring.domain.post.service;

import com.wbm.scenergyspring.domain.post.entity.Video;
import com.wbm.scenergyspring.domain.post.entity.VideoPost;
import com.wbm.scenergyspring.domain.post.repository.VideoPostGenreTagRepository;
import com.wbm.scenergyspring.domain.post.repository.VideoPostInstrumentTagRepository;
import com.wbm.scenergyspring.domain.post.repository.VideoPostRepository;
import com.wbm.scenergyspring.domain.post.repository.VideoRepository;
import com.wbm.scenergyspring.domain.post.service.command.CreateVideoCommand;
import com.wbm.scenergyspring.domain.post.service.command.VideoPostCommand;
import com.wbm.scenergyspring.domain.post.service.command.VideoPostCommandResponse;
import com.wbm.scenergyspring.domain.tag.entity.GenreTag;
import com.wbm.scenergyspring.domain.tag.entity.InstrumentTag;
import com.wbm.scenergyspring.domain.tag.repository.GenreTagRepository;
import com.wbm.scenergyspring.domain.tag.repository.InstrumentTagRepository;
import com.wbm.scenergyspring.domain.user.entity.User;
import com.wbm.scenergyspring.domain.user.repository.UserRepository;
import com.wbm.scenergyspring.global.exception.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class VideoPostServiceTest {

    @Autowired
    private VideoPostService videoPostService;
    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private VideoPostRepository videoPostRepository;
    @Autowired
    private VideoPostGenreTagRepository videoPostGenreTagRepository;
    @Autowired
    private VideoPostInstrumentTagRepository videoPostInstrumentTagRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GenreTagRepository genreTagRepository;
    @Autowired
    private InstrumentTagRepository instrumentTagRepository;

    @Test
    @DisplayName("모든 videoPost 조회 테스트")
    void getAllVideoPost() {
        //given
        User testUser = User.createNewUser(
                "testEmail",
                "testPassword",
                "testNickname"
        );
        User user = userRepository.save(testUser);

        CreateVideoCommand command = CreateVideoCommand.builder()
                .videoTitle("testVideoTitle")
                .artist("testArtist")
                .videoUrlPath("testVideoUrlPath")
                .thumbnailUrlPath("testThumbnailUrlPath")
                .build();
        Video video1 = Video.createVideo(command);
        Video video2 = Video.createVideo(command);
        videoRepository.save(video1);
        videoRepository.save(video2);

        genreTagRepository.save(GenreTag.createGenreTag("Jazz"));
        genreTagRepository.save(GenreTag.createGenreTag("Pop"));
        genreTagRepository.save(GenreTag.createGenreTag("rock"));
        instrumentTagRepository.save(InstrumentTag.createInstrumentTag("기타"));
        instrumentTagRepository.save(InstrumentTag.createInstrumentTag("드럼"));
        instrumentTagRepository.save(InstrumentTag.createInstrumentTag("베이스"));

        List<Long> genreTagIds = new ArrayList<>();
        genreTagIds.add(1L);
        genreTagIds.add(2L);
        genreTagIds.add(3L);
        List<Long> instrumentTagIds = new ArrayList<>();
        instrumentTagIds.add(1L);
        instrumentTagIds.add(2L);
        instrumentTagIds.add(3L);

        VideoPostCommand command1 = VideoPostCommand.builder()
                .userId(user.getId())
                .video(video1)
                .title("testTitle")
                .content("testContent")
                .instrumentTagIds(instrumentTagIds)
                .genreTagIds(genreTagIds)
                .build();

        VideoPostCommand command2 = VideoPostCommand.builder()
                .userId(user.getId())
                .video(video2)
                .title("testTitle2")
                .content("testContent2")
                .instrumentTagIds(instrumentTagIds)
                .genreTagIds(genreTagIds)
                .build();

        VideoPost videoPost1 = videoPostService.createVideoPost(command1);
        VideoPost videoPost2 = videoPostService.createVideoPost(command2);
        videoPostRepository.save(videoPost1);
        videoPostRepository.save(videoPost2);
        //when
        List<VideoPostCommandResponse> allVideoPost = videoPostService.getAllVideoPost();
        //then
        assertThat(allVideoPost.size()).isEqualTo(2);
        assertThat(allVideoPost.get(0).getVideo()).isEqualTo(videoPost1.getVideo());
        assertThat(allVideoPost.get(0).getTitle()).isEqualTo(videoPost1.getTitle());
        assertThat(allVideoPost.get(0).getContent()).isEqualTo(videoPost1.getContent());
        assertThat(allVideoPost.get(0).getUserId()).isEqualTo(videoPost1.getUser().getId());
    }

    @Test
    void getVideoPost() {
        //given
        User testUser = User.createNewUser(
                "testEmail",
                "testPassword",
                "testNickname"
        );
        User user = userRepository.save(testUser);

        CreateVideoCommand command = CreateVideoCommand.builder()
                .videoTitle("testVideoTitle")
                .artist("testArtist")
                .videoUrlPath("testVideoUrlPath")
                .thumbnailUrlPath("testThumbnailUrlPath")
                .build();
        Video video = Video.createVideo(command);
        videoRepository.save(video);

        genreTagRepository.save(GenreTag.createGenreTag("Jazz"));
        genreTagRepository.save(GenreTag.createGenreTag("Pop"));
        genreTagRepository.save(GenreTag.createGenreTag("rock"));
        instrumentTagRepository.save(InstrumentTag.createInstrumentTag("기타"));
        instrumentTagRepository.save(InstrumentTag.createInstrumentTag("드럼"));
        instrumentTagRepository.save(InstrumentTag.createInstrumentTag("베이스"));

        List<Long> genreTagIds = new ArrayList<>();
        genreTagIds.add(1L);
        genreTagIds.add(2L);
        genreTagIds.add(3L);
        List<Long> instrumentTagIds = new ArrayList<>();
        instrumentTagIds.add(1L);
        instrumentTagIds.add(2L);
        instrumentTagIds.add(3L);

        VideoPostCommand command1 = VideoPostCommand.builder()
                .userId(user.getId())
                .video(video)
                .title("testTitle")
                .content("testContent")
                .instrumentTagIds(instrumentTagIds)
                .genreTagIds(genreTagIds)
                .build();

        VideoPost videoPost1 = videoPostService.createVideoPost(command1);
        //when
        VideoPostCommandResponse testVideoPost = videoPostService.getVideoPost(1L);
        //then
        assertThat(videoPost1.getVideo()).isEqualTo(testVideoPost.getVideo());
        assertThat(videoPost1.getTitle()).isEqualTo(testVideoPost.getTitle());
        assertThat(videoPost1.getContent()).isEqualTo(testVideoPost.getContent());
        assertThat(videoPost1.getWriter()).isEqualTo(testVideoPost.getWriter());
        assertThat(videoPost1.getVideoPostGenreTags().get(0).getGenreTag()).isEqualTo(testVideoPost.getGenreTags().get(0).getGenreTag());
        assertThat(videoPost1.getVideoPostGenreTags().get(1).getGenreTag()).isEqualTo(testVideoPost.getGenreTags().get(1).getGenreTag());
        assertThat(videoPost1.getVideoPostInstrumentTags().get(0).getVideoPost()).isEqualTo(testVideoPost.getInstrumentTags().get(0).getVideoPost());
        assertThat(videoPost1.getVideoPostInstrumentTags().get(0).getInstrumentTag()).isEqualTo(testVideoPost.getInstrumentTags().get(0).getInstrumentTag());
        assertThat(videoPost1.getVideoPostInstrumentTags().get(1).getVideoPost()).isEqualTo(testVideoPost.getInstrumentTags().get(1).getVideoPost());
        assertThat(videoPost1.getVideoPostInstrumentTags().get(1).getInstrumentTag()).isEqualTo(testVideoPost.getInstrumentTags().get(1).getInstrumentTag());

    }

    @Test
    void uploadJustVideoS3() throws IOException {
        //given
        //when
        //then
    }

    @Test
    void uploadThumbnailS3() {
        //given
        //when
        //then
    }

    @Test
    @DisplayName("Video 생성 테스트")
    void createVideo() {
        //given
        String videoUrlPath = "videoUrlPath";
        String thumbnailUrlPath = "thumbnailUrlPath";
        String videoTitle = "videoTitle";
        String videoArtist = "videoArtist";
        CreateVideoCommand command = CreateVideoCommand.builder()
                .videoUrlPath(videoUrlPath)
                .thumbnailUrlPath(thumbnailUrlPath)
                .videoTitle(videoTitle)
                .artist(videoArtist)
                .build();
        Video video = Video.createVideo(command);
        //when
        Video testVideo = videoRepository.save(video);
        //then
        assertThat(video).isEqualTo(testVideo);
        assertThat(video.getVideoUrlPath()).isEqualTo(testVideo.getVideoUrlPath());
        assertThat(video.getThumbnailUrlPath()).isEqualTo(testVideo.getThumbnailUrlPath());
        assertThat(video.getMusicTitle()).isEqualTo(testVideo.getMusicTitle());
        assertThat(video.getArtist()).isEqualTo(testVideo.getArtist());

    }

    @Test
    @DisplayName("VideoPost 생성 테스트")
    void createVideoPost() {
        //given
        User testUser = User.createNewUser(
                "testEmail",
                "testPassword",
                "testNickname"
        );
        User user = userRepository.save(testUser);

        CreateVideoCommand command = CreateVideoCommand.builder()
                .videoTitle("testVideoTitle")
                .artist("testArtist")
                .videoUrlPath("testVideoUrlPath")
                .thumbnailUrlPath("testThumbnailUrlPath")
                .build();
        Video video = Video.createVideo(command);
        videoRepository.save(video);

        genreTagRepository.save(GenreTag.createGenreTag("Jazz"));
        genreTagRepository.save(GenreTag.createGenreTag("Pop"));
        genreTagRepository.save(GenreTag.createGenreTag("rock"));
        instrumentTagRepository.save(InstrumentTag.createInstrumentTag("기타"));
        instrumentTagRepository.save(InstrumentTag.createInstrumentTag("드럼"));
        instrumentTagRepository.save(InstrumentTag.createInstrumentTag("베이스"));

        List<Long> genreTagIds = new ArrayList<>();
        genreTagIds.add(1L);
        genreTagIds.add(2L);
        genreTagIds.add(3L);
        List<Long> instrumentTagIds = new ArrayList<>();
        instrumentTagIds.add(1L);
        instrumentTagIds.add(2L);
        instrumentTagIds.add(3L);

        VideoPostCommand testCommand = VideoPostCommand.builder()
                .userId(user.getId())
                .video(video)
                .title("testTitle")
                .content("testContent")
                .instrumentTagIds(instrumentTagIds)
                .genreTagIds(genreTagIds)
                .build();

        VideoPost videoPost = videoPostService.createVideoPost(testCommand);
        videoPostRepository.save(videoPost);
        //when
        VideoPost testVideoPost = videoPostRepository.findById(1L).orElseThrow(() -> new EntityNotFoundException(1 + "은(는) 존재하지 않는 VideoPost Id입니다."));
        //then
        assertThat(videoPost).isEqualTo(testVideoPost);
        assertThat(videoPost.getVideo()).isEqualTo(testVideoPost.getVideo());
        assertThat(videoPost.getUser()).isEqualTo(testVideoPost.getUser());
        assertThat(videoPost.getTitle()).isEqualTo(testVideoPost.getTitle());
        assertThat(videoPost.getContent()).isEqualTo(testVideoPost.getContent());
        assertThat(videoPost.getWriter()).isEqualTo(testVideoPost.getWriter());
        assertThat(videoPost.getVideoPostGenreTags()).isEqualTo(testVideoPost.getVideoPostGenreTags());
        assertThat(videoPost.getVideoPostGenreTags()).isNotNull();
        assertThat(videoPost.getVideoPostInstrumentTags()).isEqualTo(testVideoPost.getVideoPostInstrumentTags());
        assertThat(videoPost.getVideoPostInstrumentTags()).isNotNull();

    }

    @Test
    void createVideoPostGenreTags() {
        //given
        //when
        //then
    }

    @Test
    void createVideoPostInstrumentTag() {
        //given
        //when
        //then
    }

    @Test
    void updateVideoPost() {
        //given
        //when
        //then
    }

    @Test
    void deleteVideoPost() {
        //given
        //when
        //then
    }

}