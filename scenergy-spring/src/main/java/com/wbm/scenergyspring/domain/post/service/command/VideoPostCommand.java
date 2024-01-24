package com.wbm.scenergyspring.domain.post.service.command;

import com.wbm.scenergyspring.domain.post.entity.Video;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class VideoPostCommand {

    public Long userId;
    public Video video;
    public String title;
    public String content;
    public List<Long> genreTagIds;
    public List<Long> instrumentTagIds;

}
