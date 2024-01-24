package com.wbm.scenergyspring.domain.post.service.command;

import com.wbm.scenergyspring.domain.post.entity.VideoPost;
import com.wbm.scenergyspring.domain.tag.entity.GenreTag;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VideoPostGenreTagCommand {

    private Long id;
    private VideoPost videoPost;
    private GenreTag genreTag;

}
