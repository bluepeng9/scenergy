package com.wbm.scenergyspring.domain.post.videoPost.controller.request;

import java.util.ArrayList;
import java.util.List;

import com.wbm.scenergyspring.domain.post.videoPost.entity.Video;
import com.wbm.scenergyspring.domain.post.videoPost.service.command.CreateVideoCommand;
import com.wbm.scenergyspring.domain.post.videoPost.service.command.VideoPostCommand;

import lombok.Data;

@Data
public class UploadVideoPostRequest {

    private Long userId;
    private String postTitle;
    private String postContent;
    private List<Long> genreTags = new ArrayList<>();
    private List<Long> instrumentTags = new ArrayList<>();
    private String videoUrlPath;
    private String thumbnailUrlPath;
    private String videoTitle;
    private String videoArtist;

    public CreateVideoCommand toCreateVideo() {
        CreateVideoCommand command = CreateVideoCommand.builder()
                .videoTitle(videoTitle)
                .artist(videoArtist)
                .videoUrlPath(videoUrlPath)
                .thumbnailUrlPath(thumbnailUrlPath)
                .build();
        return command;
    }

    public VideoPostCommand toCreateVideoPost(Video video) {
        VideoPostCommand command = VideoPostCommand.builder()
                .userId(userId)
                .video(video)
                .title(postTitle)
                .content(postContent)
                .genreTagIds(genreTags)
                .instrumentTagIds(instrumentTags)
                .build();
        return command;
    }

}
