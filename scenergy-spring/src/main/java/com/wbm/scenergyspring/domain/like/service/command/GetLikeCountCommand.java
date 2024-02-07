package com.wbm.scenergyspring.domain.like.service.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetLikeCountCommand {
    private Long postId;
}
