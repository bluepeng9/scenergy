package com.wbm.scenergyspring.domain.post.service.command;

import com.wbm.scenergyspring.domain.post.entity.VideoPost;
import com.wbm.scenergyspring.domain.tag.entity.InstrumentTag;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VideoPostInstrumentTagCommand {

    private Long id;
    private VideoPost videoPost;
    private InstrumentTag instrumentTag;

}
