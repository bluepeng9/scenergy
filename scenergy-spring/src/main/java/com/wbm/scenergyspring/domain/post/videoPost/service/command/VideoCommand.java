package com.wbm.scenergyspring.domain.post.videoPost.service.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VideoCommand {

    Long id;
    String videoUrlPath;
    String thumbnailUrlPath;
    String musicTitle;
    String artist;
}
