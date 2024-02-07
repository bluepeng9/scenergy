package com.wbm.scenergyspring.domain.like.controller.request;

import com.wbm.scenergyspring.domain.like.service.command.GetLikeCountCommand;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetLikeCountRequest {
    Long post_id;

    public GetLikeCountCommand toCreateGetLikeCountCommand() {
        GetLikeCountCommand command = GetLikeCountCommand.builder()
                .postId(post_id)
                .build();
        return command;
    }
}
