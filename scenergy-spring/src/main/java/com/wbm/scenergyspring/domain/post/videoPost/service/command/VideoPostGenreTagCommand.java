package com.wbm.scenergyspring.domain.post.videoPost.service.command;

import com.wbm.scenergyspring.domain.post.videoPost.entity.VideoPostGenreTag;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class VideoPostGenreTagCommand {

    private Long id;
    private Long genreTagId;

    public static List<VideoPostGenreTagCommand> createVideoPostGenreTagCommand(List<VideoPostGenreTag> videoPostGenreTags) {
        List<VideoPostGenreTagCommand> list = new ArrayList<>();
        for (VideoPostGenreTag videoPostGenreTag : videoPostGenreTags) {
            VideoPostGenreTagCommand videoPostGenreTagCommand = VideoPostGenreTagCommand.builder()
                    .id(videoPostGenreTag.getId())
                    .genreTagId(videoPostGenreTag.getGenreTag().getId())
                    .build();
            list.add(videoPostGenreTagCommand);
        }
        return list;
    }
}
