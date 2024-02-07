package com.wbm.scenergyspring.domain.like.controller.request;

import com.wbm.scenergyspring.domain.like.service.command.UnlikePostCommand;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UnlikePostRequest {
    Long post_id;
    Long user_id;

    public UnlikePostCommand toCreateUnlikePostCommand() {
        UnlikePostCommand command = UnlikePostCommand.builder()
                .postId(post_id)
                .userId(user_id)
                .build();
        return command;
    }
}
