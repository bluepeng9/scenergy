package com.wbm.scenergyspring.domain.post.jobPost.service.Command;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SearchAllJobPostCommand {

    String name;
    List<Long> gt;
    List<Long> it;
    List<Long> lt;

}
