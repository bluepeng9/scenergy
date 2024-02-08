package com.wbm.scenergyspring.domain.post.videoPost.controller.response;

import com.wbm.scenergyspring.domain.post.videoPost.service.command.SearchVideoPostResponseCommand;
import lombok.Data;

import java.util.List;

@Data
public class SearchVideoPostResponse {

    List<SearchVideoPostResponseCommand> list;

    public static SearchVideoPostResponse toCreateResponse(List<SearchVideoPostResponseCommand> list) {
        SearchVideoPostResponse response = new SearchVideoPostResponse();
        response.list = list;
        return response;
    }

}
