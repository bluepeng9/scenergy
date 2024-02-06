package com.wbm.scenergyspring.domain.post.videoPost.controller.request;

import com.wbm.scenergyspring.domain.post.videoPost.service.command.CreateVideoCommand;
import com.wbm.scenergyspring.domain.post.videoPost.service.command.UpdatePostVideoCommand;
import com.wbm.scenergyspring.domain.post.videoPost.service.command.UpdateVideoCommand;
import lombok.Data;

import java.util.List;

@Data
public class UpdateVideoPostRequest {

    private Long postVideoId;
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

    public UpdateVideoCommand videoUpdateCommand() {
        UpdateVideoCommand command = UpdateVideoCommand.builder()
                .videoUrlPath(videoUrlPath)
                .thumbnailUrlPath(thumbnailUrlPath)
                .videoTitle(videoTitle)
                .videoArtist(videoArtist)
                .build();
        return command;
    }

    public UpdatePostVideoCommand videoPostUpdateCommand() {
        UpdatePostVideoCommand command = UpdatePostVideoCommand.builder()
                .postTitle(postTitle)
                .postContent(postContent)
                .build();
        return command;
    }

}
