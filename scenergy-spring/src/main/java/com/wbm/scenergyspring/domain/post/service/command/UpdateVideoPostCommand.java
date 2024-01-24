package com.wbm.scenergyspring.domain.post.service.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateVideoPostCommand {

    private String postTitle;
    private String postContent;

}
