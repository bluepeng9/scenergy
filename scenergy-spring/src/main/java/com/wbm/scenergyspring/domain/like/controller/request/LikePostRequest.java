package com.wbm.scenergyspring.domain.like.controller.request;

import com.wbm.scenergyspring.domain.like.service.command.LikePostCommand;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LikePostRequest {
    Long post_id;
    Long user_id;


    public LikePostCommand toCreateLikePostCommand() {
        LikePostCommand command = LikePostCommand.builder()
                .postId(post_id)
                .userId(user_id)
                .build();
        return command;
    }
}
