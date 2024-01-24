package com.wbm.scenergyspring.domain.post.controller.request;

import com.wbm.scenergyspring.domain.post.entity.Video;
import com.wbm.scenergyspring.domain.post.service.command.CreateVideoCommand;
import com.wbm.scenergyspring.domain.post.service.command.VideoPostCommand;
import lombok.Data;

import java.util.List;

@Data
public class UploadPostVideoRequest {

    private Long userId;
    private String postTitle;
    private String postContent;
    private List<Long> genreTags;
    private List<Long> instrumentTags;
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
