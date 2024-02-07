package com.wbm.scenergyspring.domain.post.jobPost.controller.request;

import com.wbm.scenergyspring.domain.post.jobPost.service.Command.SearchAllJobPostCommand;
import lombok.Data;

import java.util.List;

@Data
public class SearchAllJobPostRequest {

    String name;
    List<Long> gt;
    List<Long> it;
    List<Long> lt;

    public SearchAllJobPostCommand toSearchAllJobPostCommand() {
        SearchAllJobPostCommand command = SearchAllJobPostCommand.builder()
                .name(name)
                .gt(gt)
                .it(it)
                .lt(lt)
                .build();
        return command;
    }

}
