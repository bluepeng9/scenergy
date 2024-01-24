package com.wbm.scenergyspring.domain.post.service.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateVideoCommand {

    public String videoUrlPath;
    public String thumbnailUrlPath;
    public String videoTitle;
    public String videoArtist;

}
