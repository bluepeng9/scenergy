package com.wbm.scenergyspring.domain.post.videoPost.service.command;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FollowingVideoPostsCommand {

    private Long userId;
    private VideoCommand video;
    private String title;
    private String content;
    private String writer;
    private String url;
    private String nickname;
    private List<VideoPostGenreTagCommand> genreTags;
    private List<VideoPostInstrumentTagCommand> instrumentTags;

}
