package com.wbm.scenergyspring.domain.post.videoPost.service.command;

import com.wbm.scenergyspring.domain.post.videoPost.entity.Video;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class VideoPostCommandResponse {

    public Long userId;
    public Video video;
    public String title;
    public String content;
    public String writer;
    public List<VideoPostGenreTagCommand> genreTags;
    public List<VideoPostInstrumentTagCommand> instrumentTags;

}
