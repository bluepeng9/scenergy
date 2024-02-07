package com.wbm.scenergyspring.domain.post.videoPost.controller.request;

import lombok.Data;

import java.util.List;

@Data
public class SearchVideoPostRequest {

    String word;
    List<Long> gt;
    List<Long> it;

}
