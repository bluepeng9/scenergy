package com.wbm.scenergyspring.domain.post.entity;

import com.wbm.scenergyspring.domain.post.service.command.UpdateVideoPostCommand;
import com.wbm.scenergyspring.domain.post.service.command.VideoPostGenreTagCommand;
import com.wbm.scenergyspring.domain.post.service.command.VideoPostInstrumentTagCommand;
import com.wbm.scenergyspring.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class VideoPost extends Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id")
    Video video;

    @OneToMany(mappedBy = "videoPost", cascade = CascadeType.ALL)
    private List<VideoPostGenreTag> videoPostGenreTags = new ArrayList<>();

    @OneToMany(mappedBy = "videoPost", cascade = CascadeType.ALL)
    private List<VideoPostInstrumentTag> videoPostInstrumentTags = new ArrayList<>();

    public static VideoPost createVideoPost(
        User user,
        Video video,
        String title,
        String content,
        String writer
    ){
        VideoPost videopost = new VideoPost();
        videopost.user = user;
        videopost.video = video;
        videopost.title = title;
        videopost.content = content;
        videopost.writer = writer;

        return videopost;
    }

    public void updateVideoPostGenreTags(List<VideoPostGenreTag> videoPostGenreTags) {
        this.videoPostGenreTags = videoPostGenreTags;
    }

    public void updateVideoPostInstrumentTags(List<VideoPostInstrumentTag> videoPostInstrumentTags) {
        this.videoPostInstrumentTags = videoPostInstrumentTags;
    }

    public void updateVideoPost(UpdateVideoPostCommand command) {
        if (command.getPostTitle() != null)
            title = command.getPostTitle();
        if (command.getPostContent() != null)
            content = command.getPostContent();
    }

    public List<VideoPostGenreTagCommand> createVideoPostGenreTagCommand() {
        List<VideoPostGenreTagCommand> list = new ArrayList<>();
        for (VideoPostGenreTag videoPostGenreTag : videoPostGenreTags) {
            VideoPostGenreTagCommand videoPostGenreTagCommand = VideoPostGenreTagCommand.builder()
                    .id(videoPostGenreTag.getId())
                    .genreTag(videoPostGenreTag.getGenreTag())
                    .videoPost(videoPostGenreTag.getVideoPost())
                    .build();
        }
        return list;
    }

    public List<VideoPostInstrumentTagCommand> createVideoPostInstrumentTagCommand() {
        List<VideoPostInstrumentTagCommand> list = new ArrayList<>();
        for (VideoPostInstrumentTag videoPostInstrumentTag : videoPostInstrumentTags) {
            VideoPostInstrumentTagCommand videoPostInstrumentTagCommand = VideoPostInstrumentTagCommand.builder()
                    .id(videoPostInstrumentTag.getId())
                    .instrumentTag(videoPostInstrumentTag.getInstrumentTag())
                    .videoPost(videoPostInstrumentTag.getVideoPost())
                    .build();
        }
        return list;
    }

    public void deleteVideoPostGenreTags() {
        videoPostGenreTags = null;
    }

    public void deleteVideoPostInstrumentTags() {
        videoPostInstrumentTags = null;
    }
}
