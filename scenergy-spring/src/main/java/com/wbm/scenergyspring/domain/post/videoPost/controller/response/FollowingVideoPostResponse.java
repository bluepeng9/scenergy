package com.wbm.scenergyspring.domain.post.videoPost.controller.response;

import com.wbm.scenergyspring.domain.post.videoPost.service.command.FollowingVideoPostsCommand;
import lombok.Data;

import java.util.List;

@Data
public class FollowingVideoPostResponse {

    List<FollowingVideoPostsCommand> list;

    public static FollowingVideoPostResponse toCreateResponse(List<FollowingVideoPostsCommand> list) {
        FollowingVideoPostResponse response = new FollowingVideoPostResponse();
        response.list = list;
        return response;
    }
}
