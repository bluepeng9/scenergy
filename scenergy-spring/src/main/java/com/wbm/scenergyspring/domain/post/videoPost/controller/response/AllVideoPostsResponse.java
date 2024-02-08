package com.wbm.scenergyspring.domain.post.videoPost.controller.response;

import com.wbm.scenergyspring.domain.post.videoPost.service.command.AllVideoPostsCommand;
import lombok.Data;

import java.util.List;

@Data
public class AllVideoPostsResponse {

    List<AllVideoPostsCommand> list;

    public static AllVideoPostsResponse toCreateResponse(List<AllVideoPostsCommand> list) {
        AllVideoPostsResponse response = new AllVideoPostsResponse();
        response.list = list;
        return response;
    }

}
