package com.wbm.scenergyspring.domain.like.controller.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetLikeCountResponse {
    int likeCount;
}
