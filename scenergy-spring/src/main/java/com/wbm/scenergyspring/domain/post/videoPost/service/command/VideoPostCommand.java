package com.wbm.scenergyspring.domain.post.videoPost.service.command;

import java.util.ArrayList;
import java.util.List;

import com.wbm.scenergyspring.domain.post.videoPost.entity.Video;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VideoPostCommand {

    public Long userId;
    public Video video;
    public String title;
    public String content;
    public List<Long> genreTagIds = new ArrayList<>();
    public List<Long> instrumentTagIds = new ArrayList<>();

}
