package com.wbm.scenergyspring.domain.post.videoPost.service.command;

import com.wbm.scenergyspring.domain.post.videoPost.entity.VideoPostInstrumentTag;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class VideoPostInstrumentTagCommand {

    private Long id;
    private Long instrumentTagId;

    public static List<VideoPostInstrumentTagCommand> createVideoPostInstrumentTagCommand(List<VideoPostInstrumentTag> videoPostInstrumentTags) {
        List<VideoPostInstrumentTagCommand> list = new ArrayList<>();
        for (VideoPostInstrumentTag videoPostInstrumentTag : videoPostInstrumentTags) {
            VideoPostInstrumentTagCommand videoPostInstrumentTagCommand = VideoPostInstrumentTagCommand.builder()
                    .id(videoPostInstrumentTag.getId())
                    .instrumentTagId(videoPostInstrumentTag.getInstrumentTag().getId())
                    .build();
            list.add(videoPostInstrumentTagCommand);
        }
        return list;
    }

}
