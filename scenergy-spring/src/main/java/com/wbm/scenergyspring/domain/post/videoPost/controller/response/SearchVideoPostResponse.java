package com.wbm.scenergyspring.domain.post.videoPost.controller.response;

import com.wbm.scenergyspring.domain.post.videoPost.service.command.VideoCommand;
import com.wbm.scenergyspring.domain.post.videoPost.service.command.VideoPostGenreTagCommand;
import com.wbm.scenergyspring.domain.post.videoPost.service.command.VideoPostInstrumentTagCommand;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SearchVideoPostResponse {

    private VideoCommand video;
    private String title;
    private String content;
    private String writer;
    private List<VideoPostGenreTagCommand> genreTags;
    private List<VideoPostInstrumentTagCommand> instrumentTags;

}
