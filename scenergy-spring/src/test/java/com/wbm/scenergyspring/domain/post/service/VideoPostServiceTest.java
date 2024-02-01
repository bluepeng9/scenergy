package com.wbm.scenergyspring.domain.post.service;

import com.wbm.scenergyspring.domain.follow.entity.Follow;
import com.wbm.scenergyspring.domain.follow.repository.FollowRepository;
import com.wbm.scenergyspring.domain.post.videoPost.controller.request.UpdateVideoPostRequest;
import com.wbm.scenergyspring.domain.post.videoPost.entity.Video;
import com.wbm.scenergyspring.domain.post.videoPost.entity.VideoPost;
import com.wbm.scenergyspring.domain.post.videoPost.repository.VideoPostRepository;
import com.wbm.scenergyspring.domain.post.videoPost.repository.VideoRepository;
import com.wbm.scenergyspring.domain.post.videoPost.service.VideoPostService;
import com.wbm.scenergyspring.domain.post.videoPost.service.command.CreateVideoCommand;
import com.wbm.scenergyspring.domain.post.videoPost.service.command.VideoPostCommand;
import com.wbm.scenergyspring.domain.post.videoPost.service.command.VideoPostCommandResponse;
import com.wbm.scenergyspring.domain.tag.entity.GenreTag;
import com.wbm.scenergyspring.domain.tag.entity.InstrumentTag;
import com.wbm.scenergyspring.domain.tag.repository.GenreTagRepository;
import com.wbm.scenergyspring.domain.tag.repository.InstrumentTagRepository;
import com.wbm.scenergyspring.domain.user.entity.Gender;
import com.wbm.scenergyspring.domain.user.entity.User;
import com.wbm.scenergyspring.domain.user.repository.UserRepository;
import com.wbm.scenergyspring.global.exception.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class VideoPostServiceTest {

    @Autowired
    private VideoPostService videoPostService;
    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private VideoPostRepository videoPostRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GenreTagRepository genreTagRepository;
    @Autowired
    private InstrumentTagRepository instrumentTagRepository;
    @Autowired
    private FollowRepository followRepository;

    @Test
    @Transactional
    @DisplayName("모든 videoPost 조회 테스트")
    void getAllVideoPost() {
        //given
        User user = createTestUser();
        userRepository.save(user);

        CreateVideoCommand command = createVideoCommand();
        Video video1 = Video.createVideo(command);
        Video video2 = Video.createVideo(command);
        videoRepository.save(video1);
        videoRepository.save(video2);

        createGenreTags();
        createInstrumentTags();

        List<Long> genreTagIds = createGenreTagIds();
        List<Long> instrumentTagIds = createInstrumentsTagIds();

        VideoPostCommand command1 = createVideoPostCommand(user, video1, genreTagIds, instrumentTagIds);
        VideoPostCommand command2 = createVideoPostCommand(user, video2, genreTagIds, instrumentTagIds);

        VideoPost videoPost1 = videoPostService.createVideoPost(command1);
        VideoPost videoPost2 = videoPostService.createVideoPost(command2);
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
    @Transactional
    @DisplayName("VideoPost 조회 테스트")
    void getVideoPost() {
        //given
        User user = createTestUser();
        userRepository.save(user);

        CreateVideoCommand command = createVideoCommand();
        Video video1 = Video.createVideo(command);
        videoRepository.save(video1);

        createGenreTags();
        createInstrumentTags();

        List<Long> genreTagIds = createGenreTagIds();
        List<Long> instrumentTagIds = createInstrumentsTagIds();

        VideoPostCommand command1 = createVideoPostCommand(user, video1, genreTagIds, instrumentTagIds);

        VideoPost videoPost1 = videoPostService.createVideoPost(command1);
        //when
        VideoPostCommandResponse testVideoPost = videoPostService.getVideoPost(videoPost1.getId());
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
    @Transactional
    @DisplayName("팔로잉 게시물 조회")
    public void getFollowingVideoPosts() {
        //given
        User user1 = User.createNewUser(
                "email1",
                "password1",
                "tester1",
                Gender.MALE,
                "nickname1"
        );
        User user2 = User.createNewUser(
                "email2",
                "password2",
                "tester2",
                Gender.MALE,
                "nickname2"
        );
        User user3 = User.createNewUser(
                "email3",
                "password3",
                "tester3",
                Gender.MALE,
                "nickname3"
        );
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        Follow follow12 = Follow.createFollow(user1, user2);
        Follow follow13 = Follow.createFollow(user1, user3);

        followRepository.save(follow12);
        followRepository.save(follow13);

        CreateVideoCommand command = createVideoCommand();
        Video video1 = Video.createVideo(command);
        Video video2 = Video.createVideo(command);
        Video video3 = Video.createVideo(command);
        Video video4 = Video.createVideo(command);
        videoRepository.save(video1);
        videoRepository.save(video2);
        videoRepository.save(video3);
        videoRepository.save(video4);
        List<Long> genreTagIds = new ArrayList<>();
        List<Long> instrumentsTagIds = new ArrayList<>();
        VideoPostCommand command1 = createVideoPostCommand(user2, video1, genreTagIds, instrumentsTagIds);
        VideoPostCommand command2 = createVideoPostCommand(user2, video2, genreTagIds, instrumentsTagIds);
        VideoPostCommand command3 = createVideoPostCommand(user3, video3, genreTagIds, instrumentsTagIds);
        VideoPostCommand command4 = createVideoPostCommand(user1, video4, genreTagIds, instrumentsTagIds);

        VideoPost videoPost1 = videoPostService.createVideoPost(command1);
        VideoPost videoPost2 = videoPostService.createVideoPost(command2);
        VideoPost videoPost3 = videoPostService.createVideoPost(command3);
        VideoPost videoPost4 = videoPostService.createVideoPost(command4);

        //when
        List<VideoPostCommandResponse> resultList = videoPostService.getFollowingVideoPost(user1.getId());
        //then
        assertThat(resultList.size()).isEqualTo(3);
        assertThat(resultList.get(0).getUserId()).isEqualTo(videoPost1.getUser().getId());
        assertThat(resultList.get(0).getVideo()).isEqualTo(videoPost1.getVideo());
        assertThat(resultList.get(0).getTitle()).isEqualTo(videoPost1.getTitle());
        assertThat(resultList.get(0).getContent()).isEqualTo(videoPost1.getContent());
        assertThat(resultList.get(0).getWriter()).isEqualTo(videoPost1.getWriter());
    }

    @Test
    void uploadJustVideoS3() {
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
    @Transactional
    @DisplayName("Video 생성 테스트")
    void createVideo() {
        //given
        CreateVideoCommand command = createVideoCommand();
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
    @Transactional
    @DisplayName("VideoPost & VideoPostGenreTag & VideoPostInstrumentTag 생성 테스트")
    void createVideoPost() {
        //given
        User user = createTestUser();
        userRepository.save(user);

        CreateVideoCommand command = createVideoCommand();
        Video video = Video.createVideo(command);
        videoRepository.save(video);

        createGenreTags();
        createInstrumentTags();

        List<Long> genreTagIds = createGenreTagIds();
        List<Long> instrumentTagIds = createInstrumentsTagIds();

        VideoPostCommand testCommand = createVideoPostCommand(user, video, genreTagIds, instrumentTagIds);

        VideoPost videoPost = videoPostService.createVideoPost(testCommand);
        videoPostRepository.save(videoPost);
        //when
        VideoPost testVideoPost = videoPostRepository.findById(videoPost.getId()).orElseThrow(() -> new EntityNotFoundException(1 + "은(는) 존재하지 않는 VideoPost Id입니다."));
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
    @Transactional
    @DisplayName("VideoPost & VideoPostGenreTag & VideoPostInstrumentTag 업데이트 테스트")
    void updateVideoPost() {
        //given
        User user = createTestUser();
        userRepository.save(user);
        CreateVideoCommand command = createVideoCommand();

        Video video = Video.createVideo(command);
        videoRepository.save(video);

        createGenreTags();
        createInstrumentTags();

        List<Long> genreTagIds = createGenreTagIds();
        List<Long> instrumentTagIds = createInstrumentsTagIds();

        VideoPostCommand testCommand = createVideoPostCommand(user, video, genreTagIds, instrumentTagIds);

        VideoPost videoPost = videoPostService.createVideoPost(testCommand);
        VideoPostCommandResponse testVideoPost = videoPostService.getVideoPost(videoPost.getId());
        //when
        List<Long> changedGenreTags = new ArrayList<>();
        changedGenreTags.add(1L);
        List<Long> changedInstrumentTags = new ArrayList<>();
        changedInstrumentTags.add(1L);
        UpdateVideoPostRequest request = new UpdateVideoPostRequest();
        request.setPostVideoId(1L);
        request.setPostTitle("changedPostTitle");
        request.setPostContent("changedPostContent");
        request.setGenreTags(changedGenreTags);
        request.setInstrumentTags(changedInstrumentTags);
        request.setVideoUrlPath("changedVideoUrlPath");
        request.setThumbnailUrlPath("changedThumbnailUrlPath");
        request.setVideoTitle("changedVideoTitle");
        request.setVideoArtist("changedVideoArtist");
        videoPostService.updateVideoPost(request);
        //then
        assertThat(videoPost.getVideo().getMusicTitle()).isEqualTo(request.getVideoTitle());
        assertThat(videoPost.getVideo().getArtist()).isEqualTo(request.getVideoArtist());
        assertThat(videoPost.getVideo().getVideoUrlPath()).isEqualTo(request.getVideoUrlPath());
        assertThat(videoPost.getVideo().getThumbnailUrlPath()).isEqualTo(request.getThumbnailUrlPath());
        assertThat(videoPost.getTitle()).isEqualTo(request.getPostTitle());
        assertThat(videoPost.getContent()).isEqualTo(request.getPostContent());
        assertThat(videoPost.getVideoPostGenreTags().size()).isEqualTo(request.getGenreTags().size());
        assertThat(videoPost.getVideoPostGenreTags().get(0).getGenreTag().getId()).isEqualTo(request.getGenreTags().get(0));
        assertThat(videoPost.getVideoPostInstrumentTags().size()).isEqualTo(request.getInstrumentTags().size());
        assertThat(videoPost.getVideoPostInstrumentTags().get(0).getInstrumentTag().getId()).isEqualTo(request.getInstrumentTags().get(0));
    }

    @Test
    @Transactional
    @DisplayName("VideoPost 삭제 테스트")
    void deleteVideoPost() {
        //given
        User user = createTestUser();
        userRepository.save(user);

        CreateVideoCommand command = createVideoCommand();
        Video video = Video.createVideo(command);
        videoRepository.save(video);

        createGenreTags();
        createInstrumentTags();

        List<Long> genreTagIds = createGenreTagIds();
        List<Long> instrumentTagIds = createInstrumentsTagIds();

        VideoPostCommand testCommand = createVideoPostCommand(user, video, genreTagIds, instrumentTagIds);

        VideoPost videoPost = videoPostService.createVideoPost(testCommand);
        //when
        videoPostService.deleteVideoPost(videoPost.getId());
        //then
        assertThat(videoPostRepository.count()).isEqualTo(0);
    }

    @Test
    @Transactional
    @DisplayName("모든 VideoPost 삭제 테스트")
    void deleteAllVideoPosts() {
        //give
        User user = createTestUser();
        userRepository.save(user);

        CreateVideoCommand command = createVideoCommand();
        Video video = Video.createVideo(command);
        Video video1 = Video.createVideo(command);
        videoRepository.save(video);
        videoRepository.save(video1);

        createGenreTags();
        createInstrumentTags();

        List<Long> genreTagIds = createGenreTagIds();
        List<Long> instrumentTagIds = createInstrumentsTagIds();

        VideoPostCommand testCommand = createVideoPostCommand(user, video, genreTagIds, instrumentTagIds);
        VideoPostCommand testCommand1 = createVideoPostCommand(user, video1, genreTagIds, instrumentTagIds);

        videoPostService.createVideoPost(testCommand);
        videoPostService.createVideoPost(testCommand1);
        //when
        videoPostService.deleteAllVideoPosts();
        //then
        assertThat(videoPostRepository.count()).isEqualTo(0);
    }

    public User createTestUser() {
        User testUser = User.createNewUser(
                "testEmail",
                "testPassword",
                "tester",
                Gender.MALE,
                "testNickname"
        );

        return testUser;
    }

    public CreateVideoCommand createVideoCommand() {
        CreateVideoCommand command = CreateVideoCommand.builder()
                .videoTitle("testVideoTitle")
                .artist("testArtist")
                .videoUrlPath("testVideoUrlPath")
                .thumbnailUrlPath("testThumbnailUrlPath")
                .build();

        return command;
    }

    public void createGenreTags() {
        genreTagRepository.save(GenreTag.createGenreTag("Jazz"));
        genreTagRepository.save(GenreTag.createGenreTag("Pop"));
        genreTagRepository.save(GenreTag.createGenreTag("rock"));
    }

    public void createInstrumentTags() {
        instrumentTagRepository.save(InstrumentTag.createInstrumentTag("기타"));
        instrumentTagRepository.save(InstrumentTag.createInstrumentTag("드럼"));
        instrumentTagRepository.save(InstrumentTag.createInstrumentTag("베이스"));
    }

    public VideoPostCommand createVideoPostCommand(User user, Video video, List<Long> genreTagIds, List<Long> instrumentTagIds) {
        VideoPostCommand command = VideoPostCommand.builder()
                .userId(user.getId())
                .video(video)
                .title("testTitle")
                .content("testContent")
                .instrumentTagIds(instrumentTagIds)
                .genreTagIds(genreTagIds)
                .build();
        return command;
    }

    public List<Long> createGenreTagIds() {
        List<Long> genreTagIds = new ArrayList<>();
        genreTagIds.add(genreTagRepository.findByGenreName("Jazz").get().getId());
        genreTagIds.add(genreTagRepository.findByGenreName("Pop").get().getId());
        genreTagIds.add(genreTagRepository.findByGenreName("rock").get().getId());

        return genreTagIds;
    }

    public List<Long> createInstrumentsTagIds() {
        List<Long> instrumentsTagIds = new ArrayList<>();
        instrumentsTagIds.add(instrumentTagRepository.findByInstrumentName("기타").get().getId());
        instrumentsTagIds.add(instrumentTagRepository.findByInstrumentName("드럼").get().getId());
        instrumentsTagIds.add(instrumentTagRepository.findByInstrumentName("베이스").get().getId());

        return instrumentsTagIds;
    }
}