package com.wbm.scenergyspring.domain.user.controller.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchFollowingResponse {

    Long userId;
    String nickName;
    String url;

}
