package com.wbm.scenergyspring.domain.post.videoPost.service.command;

import com.wbm.scenergyspring.domain.post.videoPost.entity.VideoPost;
import com.wbm.scenergyspring.domain.post.videoPost.entity.VideoPostInstrumentTag;
import com.wbm.scenergyspring.domain.tag.entity.InstrumentTag;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class VideoPostInstrumentTagCommand {

    private Long id;
    private VideoPost videoPost;
    private InstrumentTag instrumentTag;

    public static List<VideoPostInstrumentTagCommand> createVideoPostInstrumentTagCommand(List<VideoPostInstrumentTag> videoPostInstrumentTags) {
        List<VideoPostInstrumentTagCommand> list = new ArrayList<>();
        for (VideoPostInstrumentTag videoPostInstrumentTag : videoPostInstrumentTags) {
            VideoPostInstrumentTagCommand videoPostInstrumentTagCommand = VideoPostInstrumentTagCommand.builder()
                    .id(videoPostInstrumentTag.getId())
                    .instrumentTag(videoPostInstrumentTag.getInstrumentTag())
                    .videoPost(videoPostInstrumentTag.getVideoPost())
                    .build();
            list.add(videoPostInstrumentTagCommand);
        }
        return list;
    }

}
