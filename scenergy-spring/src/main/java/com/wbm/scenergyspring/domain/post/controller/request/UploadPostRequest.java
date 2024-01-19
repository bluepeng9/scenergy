package com.wbm.scenergyspring.domain.post.controller.request;

import com.wbm.scenergyspring.domain.post.service.command.VideoCommand;
import com.wbm.scenergyspring.domain.post.service.command.VideoPostCommand;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class UploadPostRequest {

    private MultipartFile video;
    private Long userId;
    private String postTitle;
    private String postContent;
    private String musicTitle;
    private String artist;
    private List<Long> genreTags;
    private List<Long> instrumentTags;

    public VideoCommand createVideo(){
        VideoCommand command = VideoCommand.builder()
                .musicTitle(musicTitle)
                .artist(artist)
                .build();
        return command;
    }

    public VideoPostCommand createVideoPost(){
        VideoPostCommand command = VideoPostCommand.builder()
                .userId(userId)
                .title(postTitle)
                .content(postContent)
                .build();
        return command;
    }

}
