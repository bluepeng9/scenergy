package com.wbm.scenergyspring.domain.post.controller.request;

import com.wbm.scenergyspring.domain.post.service.command.CreateVideoCommand;
import com.wbm.scenergyspring.domain.post.service.command.UpdateVideoCommand;
import com.wbm.scenergyspring.domain.post.service.command.UpdateVideoPostCommand;
import lombok.Data;

import java.util.List;

@Data
public class UpdatePostVideoRequest {

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

    public UpdateVideoPostCommand videoPostUpdateCommand() {
        UpdateVideoPostCommand command = UpdateVideoPostCommand.builder()
                .postTitle(postTitle)
                .postContent(postContent)
                .build();
        return command;
    }

}
