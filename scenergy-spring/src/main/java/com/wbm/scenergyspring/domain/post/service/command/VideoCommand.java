package com.wbm.scenergyspring.domain.post.service.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VideoCommand {

    public String videoUrl;
    public String musicTitle;
    public String thumbnail;
    public String artist;

}
