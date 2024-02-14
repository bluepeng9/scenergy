package com.wbm.scenergyspring.domain.post.videoPost.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.wbm.scenergyspring.domain.post.videoPost.controller.request.UpdateVideoPostRequest;
import com.wbm.scenergyspring.domain.post.videoPost.controller.response.MyVideoPostsResponse;
import com.wbm.scenergyspring.domain.post.videoPost.entity.Video;
import com.wbm.scenergyspring.domain.post.videoPost.entity.VideoPost;
import com.wbm.scenergyspring.domain.post.videoPost.entity.VideoPostGenreTag;
import com.wbm.scenergyspring.domain.post.videoPost.entity.VideoPostInstrumentTag;
import com.wbm.scenergyspring.domain.post.videoPost.repository.VideoPostGenreTagRepository;
import com.wbm.scenergyspring.domain.post.videoPost.repository.VideoPostInstrumentTagRepository;
import com.wbm.scenergyspring.domain.post.videoPost.repository.VideoPostRepository;
import com.wbm.scenergyspring.domain.post.videoPost.repository.VideoRepository;
import com.wbm.scenergyspring.domain.post.videoPost.service.command.*;
import com.wbm.scenergyspring.domain.tag.entity.GenreTag;
import com.wbm.scenergyspring.domain.tag.entity.InstrumentTag;
import com.wbm.scenergyspring.domain.tag.repository.GenreTagRepository;
import com.wbm.scenergyspring.domain.tag.repository.InstrumentTagRepository;
import com.wbm.scenergyspring.domain.user.entity.User;
import com.wbm.scenergyspring.domain.user.repository.UserRepository;
import com.wbm.scenergyspring.global.exception.BusinessException;
import com.wbm.scenergyspring.global.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VideoPostService {

    private final UserRepository userRepository;

    private final VideoRepository videoRepository;
    private final VideoPostRepository videoPostRepository;

    private final GenreTagRepository genreTagRepository;
    private final InstrumentTagRepository instrumentTagRepository;

    private final VideoPostGenreTagRepository videoPostGenreTagRepository;
    private final VideoPostInstrumentTagRepository videoPostInstrumentTagRepository;

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Transactional(readOnly = true)
    public List<AllVideoPostsCommand> getAllVideoPost() {
        List<AllVideoPostsCommand> list = new ArrayList<>();
        for (VideoPost videoPost : videoPostRepository.findAll()) {
            AllVideoPostsCommand command = AllVideoPostsCommand.builder()
                    .userId(videoPost.getUser().getId())
                    .title(videoPost.getTitle())
                    .content(videoPost.getContent())
                    .video(VideoCommand.builder()
                            .id(videoPost.getVideo().getId())
                            .videoUrlPath(videoPost.getVideo().getVideoUrlPath())
                            .thumbnailUrlPath(videoPost.getVideo().getThumbnailUrlPath())
                            .musicTitle(videoPost.getVideo().getMusicTitle())
                            .artist(videoPost.getVideo().getArtist())
                            .build())
                    .writer(videoPost.getWriter())
                    .genreTags(VideoPostGenreTagCommand.createVideoPostGenreTagCommand(videoPost.getVideoPostGenreTags()))
                    .instrumentTags(VideoPostInstrumentTagCommand.createVideoPostInstrumentTagCommand(videoPost.getVideoPostInstrumentTags()))
                    .build();

            list.add(command);
        }
        return list;
    }

    @Transactional(readOnly = true)
    public AllVideoPostsCommand getVideoPost(Long id) {
        VideoPost videoPost = videoPostRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id + "은(는) 존재하지 않는 VideoPost Id입니다."));
        AllVideoPostsCommand videoPostCommandResponse = AllVideoPostsCommand.builder()
                .userId(videoPost.getUser().getId())
                .title(videoPost.getTitle())
                .content(videoPost.getContent())
                .video(VideoCommand.builder()
                        .id(videoPost.getVideo().getId())
                        .videoUrlPath(videoPost.getVideo().getVideoUrlPath())
                        .thumbnailUrlPath(videoPost.getVideo().getThumbnailUrlPath())
                        .musicTitle(videoPost.getVideo().getMusicTitle())
                        .artist(videoPost.getVideo().getArtist())
                        .build())
                .writer(videoPost.getWriter())
                .genreTags(VideoPostGenreTagCommand.createVideoPostGenreTagCommand(videoPost.getVideoPostGenreTags()))
                .instrumentTags(VideoPostInstrumentTagCommand.createVideoPostInstrumentTagCommand(videoPost.getVideoPostInstrumentTags()))
                .build();
        return videoPostCommandResponse;
    }

    @Transactional(readOnly = true)
    public List<FollowingVideoPostsCommand> getFollowingVideoPost(Long id) {
        List<FollowingVideoPostsCommand> list = new ArrayList<>();
        for (VideoPost videoPost : videoPostRepository.findAllByFollowing(id)) {
            FollowingVideoPostsCommand command = FollowingVideoPostsCommand.builder()
                    .userId(videoPost.getUser().getId())
                    .title(videoPost.getTitle())
                    .content(videoPost.getContent())
                    .video(VideoCommand.builder()
                            .id(videoPost.getVideo().getId())
                            .videoUrlPath(videoPost.getVideo().getVideoUrlPath())
                            .thumbnailUrlPath(videoPost.getVideo().getThumbnailUrlPath())
                            .musicTitle(videoPost.getVideo().getMusicTitle())
                            .artist(videoPost.getVideo().getArtist())
                            .build())
                    .writer(videoPost.getWriter())
                    .genreTags(VideoPostGenreTagCommand.createVideoPostGenreTagCommand(videoPost.getVideoPostGenreTags()))
                    .instrumentTags(VideoPostInstrumentTagCommand.createVideoPostInstrumentTagCommand(videoPost.getVideoPostInstrumentTags()))
                    .build();

            list.add(command);
        }
        return list;
    }

    @Transactional(readOnly = false)
    public String uploadJustVideoS3(MultipartFile justVideo) {
        try {
            String justVideoName = UUID.randomUUID().toString().replace("-", "") + StringUtils.cleanPath(justVideo.getOriginalFilename());
            System.out.println("video/" + justVideoName);

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(justVideo.getContentType());
            metadata.setContentLength(justVideo.getSize());
            amazonS3Client.putObject(bucket, "video/" + justVideoName, justVideo.getInputStream(), metadata);

            String justVideoUrl = amazonS3Client.getUrl(bucket, "video/" + justVideoName).toString();
            return justVideoUrl;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    @Transactional(readOnly = false)
    public String uploadThumbnailS3(MultipartFile thumbnail) {
        try {
            String thumbnailName = UUID.randomUUID().toString().replace("-", "") + StringUtils.cleanPath(thumbnail.getOriginalFilename());

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(thumbnail.getContentType());
            metadata.setContentLength(thumbnail.getSize());
            amazonS3Client.putObject(bucket, "thumbnail/" + thumbnailName, thumbnail.getInputStream(), metadata);

            String thumbnailUrl = amazonS3Client.getUrl(bucket, "thumbnail/" + thumbnailName).toString();
            return thumbnailUrl;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional(readOnly = false)
    public Video createVideo(CreateVideoCommand command) {
        Video newVideo = Video.createVideo(command);
        videoRepository.save(newVideo);
        return newVideo;
    }

    @Transactional(readOnly = false)
    public VideoPost createVideoPost(VideoPostCommand command){
        User user = userRepository.findById(command.getUserId()).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자입니다."));
        VideoPost videoPost = new VideoPost();
        videoPost.createVideoPost(
                user,
                command.getVideo(),
                command.getTitle(),
                command.getContent(),
                user.getNickname()
        );
        VideoPost result = videoPostRepository.save(videoPost);
        createVideoPostGenreTags(command.getGenreTagIds(), result);
        createVideoPostInstrumentTag(command.getInstrumentTagIds(), result);
        return result;
    }

    public void createVideoPostGenreTags(List<Long> genreTagIds, VideoPost videoPost) {
        videoPost.getVideoPostGenreTags().clear();

        for (Long genreTagId : genreTagIds) {
            GenreTag genreTag = genreTagRepository.findById(genreTagId).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 지역태그입니다."));

            VideoPostGenreTag videoPostGenreTag = new VideoPostGenreTag();
            videoPostGenreTag.updateVideoPost(videoPost);
            videoPostGenreTag.updateGenreTag(genreTag);

            videoPost.getVideoPostGenreTags().add(videoPostGenreTag);
        }
    }

    public void createVideoPostInstrumentTag(List<Long> instrumentTags, VideoPost videoPost) {
        videoPost.getVideoPostInstrumentTags().clear();

        for (Long instrumentTagId : instrumentTags) {
            InstrumentTag instrumentTag = instrumentTagRepository.findById(instrumentTagId).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 악기태그입니다."));

            VideoPostInstrumentTag videoPostInstrumentTag = new VideoPostInstrumentTag();
            videoPostInstrumentTag.updateVideoPost(videoPost);
            videoPostInstrumentTag.updateInstrumentTag(instrumentTag);

            videoPost.getVideoPostInstrumentTags().add(videoPostInstrumentTag);
        }
    }

    @Transactional(readOnly = false)
    public boolean updateVideoPost(UpdateVideoPostRequest request) {
        VideoPost videoPost = videoPostRepository.findById(request.getPostVideoId()).orElseThrow(() -> new EntityNotFoundException(request.getPostVideoId() + "은(는) 존재하지 않는 VideoPost Id입니다."));

        if (request.getPostTitle() == null && request.getPostContent() == null && request.getGenreTags() == null && request.getInstrumentTags() == null &&
                request.getVideoUrlPath() == null && request.getThumbnailUrlPath() == null && request.getVideoTitle() == null && request.getVideoArtist() == null)
            return false;

        if (request.getGenreTags() != null)
            createVideoPostGenreTags(request.getGenreTags(), videoPost);
        if (request.getInstrumentTags() != null)
            createVideoPostInstrumentTag(request.getInstrumentTags(), videoPost);

        UpdatePostVideoCommand command1 = request.videoPostUpdateCommand();
        videoPost.updateVideoPost(command1);

        UpdateVideoCommand command2 = request.videoUpdateCommand();
        Video video = videoPost.getVideo();
        video.updateVideo(command2);

        return true;
    }

    @Transactional(readOnly = false)
    public boolean deleteVideoPost(Long id) {
        VideoPost videoPost = videoPostRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id + "은(는) 존재하지 않는 Id입니다."));
        videoPostRepository.delete(videoPost);
        return true;
    }

    @Transactional(readOnly = false)
    public boolean deleteAllVideoPosts() {
        if (videoPostRepository.count() == 0)
            return false;
        videoPostRepository.deleteAll();
        return true;
    }

    @Transactional(readOnly = true)
    public List<SearchVideoPostResponseCommand> searchVideoPostsByCondition(SearchVideoPostCommand command) {
        if (command.getWord() != null && command.getWord().isEmpty())
            command.setWord(null);
        if (command.getGt() != null && command.getGt().isEmpty())
            command.setGt(null);
        if (command.getIt() != null && command.getIt().isEmpty())
            command.setIt(null);
        if (command.getLt() != null && command.getLt().isEmpty())
            command.setLt(null);
        List<VideoPost> list = videoPostRepository.searchVideoPostsByCondition(command.getWord(), command.getGt(), command.getIt(), command.getLt());
        List<SearchVideoPostResponseCommand> result = new ArrayList<>();
        for (VideoPost vp : list) {
            SearchVideoPostResponseCommand response = SearchVideoPostResponseCommand.builder()
                    .userId(vp.getUser().getId())
                    .videoPostId(vp.getId())
                    .title(vp.getTitle())
                    .content(vp.getContent())
                    .writer(vp.getWriter())
                    .url(vp.getUser().getUrl())
                    .video(VideoCommand.builder()
                            .musicTitle(vp.getVideo().getMusicTitle())
                            .artist(vp.getVideo().getArtist())
                            .videoUrlPath(vp.getVideo().getVideoUrlPath())
                            .thumbnailUrlPath(vp.getVideo().getThumbnailUrlPath())
                            .build())
                    .genreTags(VideoPostGenreTagCommand.createVideoPostGenreTagCommand(vp.getVideoPostGenreTags()))
                    .instrumentTags(VideoPostInstrumentTagCommand.createVideoPostInstrumentTagCommand(vp.getVideoPostInstrumentTags()))
                    .build();
            result.add(response);
        }
        return result;
    }

    @Transactional(readOnly = true)
    public MyVideoPostsResponse getMyVideoPosts(Long userId) {
        List<VideoPost> myVideoPosts = videoPostRepository.getMyVideoPosts(userId).orElseThrow(() -> new BusinessException("영상을 찾을 수 없습니다."));
        List<MyVideoPostsResponseCommand> result = new ArrayList<>();
        for (VideoPost vp : myVideoPosts) {
            MyVideoPostsResponseCommand response = MyVideoPostsResponseCommand.builder()
                    .title(vp.getTitle())
                    .content(vp.getContent())
                    .writer(vp.getWriter())
                    .video(VideoCommand.builder()
                            .musicTitle(vp.getVideo().getMusicTitle())
                            .artist(vp.getVideo().getArtist())
                            .videoUrlPath(vp.getVideo().getVideoUrlPath())
                            .thumbnailUrlPath(vp.getVideo().getThumbnailUrlPath())
                            .build())
                    .genreTags(VideoPostGenreTagCommand.createVideoPostGenreTagCommand(vp.getVideoPostGenreTags()))
                    .instrumentTags(VideoPostInstrumentTagCommand.createVideoPostInstrumentTagCommand(vp.getVideoPostInstrumentTags()))
                    .build();
            result.add(response);
        }
        return MyVideoPostsResponse.toCreateResponse(result);
    }

}
