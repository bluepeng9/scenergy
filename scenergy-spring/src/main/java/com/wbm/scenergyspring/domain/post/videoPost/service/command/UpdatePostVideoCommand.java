package com.wbm.scenergyspring.domain.post.videoPost.service.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdatePostVideoCommand {

    private String postTitle;
    private String postContent;
    private String postWriter;

}
