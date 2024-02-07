package com.wbm.scenergyspring.domain.user.controller.request;

import lombok.Data;

@Data
public class SearchFollowingRequest {

    Long userId;
    String word;

}
