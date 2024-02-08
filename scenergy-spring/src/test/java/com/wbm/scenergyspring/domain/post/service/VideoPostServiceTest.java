package com.wbm.scenergyspring.domain.post.service;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.wbm.scenergyspring.IntegrationTest;
import com.wbm.scenergyspring.domain.follow.entity.Follow;
import com.wbm.scenergyspring.domain.follow.repository.FollowRepository;
import com.wbm.scenergyspring.domain.post.videoPost.controller.request.UpdateVideoPostRequest;
import com.wbm.scenergyspring.domain.post.videoPost.entity.Video;
import com.wbm.scenergyspring.domain.post.videoPost.entity.VideoPost;
import com.wbm.scenergyspring.domain.post.videoPost.repository.VideoPostGenreTagRepository;
import com.wbm.scenergyspring.domain.post.videoPost.repository.VideoPostInstrumentTagRepository;
import com.wbm.scenergyspring.domain.post.videoPost.repository.VideoPostRepository;
import com.wbm.scenergyspring.domain.post.videoPost.repository.VideoRepository;
import com.wbm.scenergyspring.domain.post.videoPost.service.VideoPostService;
import com.wbm.scenergyspring.domain.post.videoPost.service.command.CreateVideoCommand;
import com.wbm.scenergyspring.domain.post.videoPost.service.command.VideoPostCommand;
import com.wbm.scenergyspring.domain.post.videoPost.service.command.VideoPostCommandResponse;
import com.wbm.scenergyspring.domain.tag.entity.GenreTag;
import com.wbm.scenergyspring.domain.tag.entity.InstrumentTag;
import com.wbm.scenergyspring.domain.tag.entity.LocationTag;
import com.wbm.scenergyspring.domain.tag.repository.GenreTagRepository;
import com.wbm.scenergyspring.domain.tag.repository.InstrumentTagRepository;
import com.wbm.scenergyspring.domain.tag.repository.LocationTagRepository;
import com.wbm.scenergyspring.domain.tag.repository.UserLocationTagRepository;
import com.wbm.scenergyspring.domain.user.entity.Gender;
import com.wbm.scenergyspring.domain.user.entity.User;
import com.wbm.scenergyspring.domain.user.repository.UserRepository;
import com.wbm.scenergyspring.domain.user.service.UserService;
import com.wbm.scenergyspring.global.exception.EntityNotFoundException;

@SpringBootTest
class VideoPostServiceTest extends IntegrationTest {

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
    @Autowired
    private VideoPostGenreTagRepository videoPostGenreTagRepository;
    @Autowired
    private VideoPostInstrumentTagRepository videoPostInstrumentTagRepository;
    @Autowired
    private LocationTagRepository locationTagRepository;
    @Autowired
    private UserLocationTagRepository userLocationTagRepository;
    @Autowired
    private UserService userService;

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
        assertThat(allVideoPost.get(0).getVideo().getVideoUrlPath()).isEqualTo(videoPost1.getVideo().getVideoUrlPath());
        assertThat(allVideoPost.get(0).getVideo().getThumbnailUrlPath()).isEqualTo(videoPost1.getVideo().getThumbnailUrlPath());
        assertThat(allVideoPost.get(0).getVideo().getMusicTitle()).isEqualTo(videoPost1.getVideo().getMusicTitle());
        assertThat(allVideoPost.get(0).getVideo().getArtist()).isEqualTo(videoPost1.getVideo().getArtist());
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
        assertThat(videoPost1.getVideo().getVideoUrlPath()).isEqualTo(testVideoPost.getVideo().getVideoUrlPath());
        assertThat(videoPost1.getVideo().getThumbnailUrlPath()).isEqualTo(testVideoPost.getVideo().getThumbnailUrlPath());
        assertThat(videoPost1.getVideo().getMusicTitle()).isEqualTo(testVideoPost.getVideo().getMusicTitle());
        assertThat(videoPost1.getVideo().getArtist()).isEqualTo(testVideoPost.getVideo().getArtist());
        assertThat(videoPost1.getTitle()).isEqualTo(testVideoPost.getTitle());
        assertThat(videoPost1.getContent()).isEqualTo(testVideoPost.getContent());
        assertThat(videoPost1.getWriter()).isEqualTo(testVideoPost.getWriter());
        assertThat(videoPost1.getVideoPostGenreTags().get(0).getGenreTag().getId()).isEqualTo(testVideoPost.getGenreTags().get(0).getGenreTagId());
        assertThat(videoPost1.getVideoPostGenreTags().get(1).getGenreTag().getId()).isEqualTo(testVideoPost.getGenreTags().get(1).getGenreTagId());
        assertThat(videoPost1.getVideoPostInstrumentTags().get(0).getInstrumentTag().getId()).isEqualTo(testVideoPost.getInstrumentTags().get(0).getInstrumentTagId());
        assertThat(videoPost1.getVideoPostInstrumentTags().get(0).getInstrumentTag().getId()).isEqualTo(testVideoPost.getInstrumentTags().get(0).getInstrumentTagId());

    }

    @Test
    @Transactional
    @DisplayName("팔로잉 게시물 조회")
    public void getFollowingVideoPosts() {
        //given
        User user1 = User.createNewUser(
                "email1",
                "password1",
                "username1",
                Gender.MALE,
                "nickname1"
        );
        User user2 = User.createNewUser(
                "email2",
                "password2",
                "username2",
                Gender.MALE,
                "nickname2"
        );
        User user3 = User.createNewUser(
                "email3",
                "password3",
                "username3",
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
        assertThat(resultList.get(0).getVideo().getVideoUrlPath()).isEqualTo(videoPost1.getVideo().getVideoUrlPath());
        assertThat(resultList.get(0).getVideo().getThumbnailUrlPath()).isEqualTo(videoPost1.getVideo().getThumbnailUrlPath());
        assertThat(resultList.get(0).getVideo().getMusicTitle()).isEqualTo(videoPost1.getVideo().getMusicTitle());
        assertThat(resultList.get(0).getVideo().getArtist()).isEqualTo(videoPost1.getVideo().getArtist());
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
        changedGenreTags.add(genreTagIds.get(0));
        List<Long> changedInstrumentTags = new ArrayList<>();
        changedInstrumentTags.add(instrumentTagIds.get(0));
        UpdateVideoPostRequest request = new UpdateVideoPostRequest();
        request.setPostVideoId(videoPost.getId());
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
        assertThat(videoPostGenreTagRepository.findByVideoPost(videoPost).get().size()).isEqualTo(1);
        assertThat(videoPostInstrumentTagRepository.findByVideoPost(videoPost).get().size()).isEqualTo(1);
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

    @Test
    @Transactional
    @DisplayName("VideoPost 검색 테스트")
    void searchVideoPost() {
        //given
        User user1 = User.createNewUser(
                "email1",
                "password1",
                "username1",
                Gender.MALE,
                "nickname1"
        );
        User user2 = User.createNewUser(
                "email2",
                "password2",
                "username2",
                Gender.MALE,
                "nickname2"
        );
        User user3 = User.createNewUser(
                "email3",
                "password3",
                "username3",
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
        createGenreTags();
        createInstrumentTags();
        List<Long> genreTagIds = createGenreTagIds();
        List<Long> instrumentsTagIds = createInstrumentsTagIds();
        List<Long> genreTagIds2 = new ArrayList<>();
        List<Long> instrumentsTagIds2 = new ArrayList<>();
        VideoPostCommand command1 = createVideoPostCommand(user2, video1, genreTagIds, instrumentsTagIds);
        VideoPostCommand command2 = createVideoPostCommand(user2, video2, genreTagIds, instrumentsTagIds);
        VideoPostCommand command3 = createVideoPostCommand(user3, video3, genreTagIds2, instrumentsTagIds2);
        VideoPostCommand command4 = createVideoPostCommand(user1, video4, genreTagIds2, instrumentsTagIds2);

        VideoPost videoPost1 = videoPostService.createVideoPost(command1);
        VideoPost videoPost2 = videoPostService.createVideoPost(command2);
        VideoPost videoPost3 = videoPostService.createVideoPost(command3);
        VideoPost videoPost4 = videoPostService.createVideoPost(command4);
        Long locationTagId1 = createLocationTags("서울");
        Long locationTagId2 = createLocationTags("대전");
        Long locationTagId3 = createLocationTags("부산");
        List<Long> gt = new ArrayList<>();
        gt.add(genreTagIds.get(0));
        gt.add(genreTagIds.get(1));
        List<Long> it = new ArrayList<>();
        it.add(instrumentsTagIds.get(0));
        it.add(instrumentsTagIds.get(1));
        List<Long> lt = new ArrayList<>();
        List<Long> lt2 = new ArrayList<>();
        lt.add(locationTagId1);
        lt.add(locationTagId2);
        lt.add(locationTagId3);
        userService.createUserLocationTags(lt, user1);

        //when
        List<VideoPost> list = videoPostRepository.searchVideoPostsByCondition("nickname2", gt, it, lt);
        List<VideoPost> list2 = videoPostRepository.searchVideoPostsByCondition(null, gt, it, lt);
        //then
        assertThat(list.size()).isEqualTo(2);
        assertThat(list2.size()).isEqualTo(2);
    }

    public User createTestUser() {
        User testUser = User.createNewUser(
                "testEmail",
                "testPassword",
                "username",
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

    public Long createLocationTags(String str) {
        return locationTagRepository.save(LocationTag.createLocationTag(str)).getId();
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