package com.wbm.scenergyspring.domain.post.videoPost.service.command;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SearchVideoPostCommand {

    String word;
    List<Long> gt;
    List<Long> it;
    List<Long> lt;

}
