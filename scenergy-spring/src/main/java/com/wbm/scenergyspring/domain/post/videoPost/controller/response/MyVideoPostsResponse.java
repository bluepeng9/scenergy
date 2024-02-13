package com.wbm.scenergyspring.domain.post.videoPost.controller.response;

import com.wbm.scenergyspring.domain.post.videoPost.service.command.MyVideoPostsResponseCommand;
import lombok.Data;

import java.util.List;

@Data
public class MyVideoPostsResponse {

    List<MyVideoPostsResponseCommand> list;

    public static MyVideoPostsResponse toCreateResponse(List<MyVideoPostsResponseCommand> commandList) {
        MyVideoPostsResponse response = new MyVideoPostsResponse();
        response.list = commandList;
        return response;
    }

}
