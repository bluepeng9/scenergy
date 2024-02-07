package com.wbm.scenergyspring.domain.like.controller.request;

import com.wbm.scenergyspring.domain.like.service.command.GetLikeListCommand;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetLikeListRequest {
    Long post_id;

    public GetLikeListCommand toCreateGetLikeListCommand() {
        GetLikeListCommand command = GetLikeListCommand.builder()
                .postId(post_id)
                .build();
        return command;
    }
}
