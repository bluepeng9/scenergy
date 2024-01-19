package com.wbm.scenergyspring.domain.post.entity;

import com.wbm.scenergyspring.domain.tag.entity.VideoPostGenreTag;
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


    public void addVideoPostGenreTag(VideoPostGenreTag videoPostGenreTag) {
        videoPostGenreTags.add(videoPostGenreTag);
        videoPostGenreTag.updateVideoPost(this);
    }

    public void removeVideoPostGenreTag(VideoPostGenreTag videoPostGenreTag) {
        videoPostGenreTags.remove(videoPostGenreTag);
        videoPostGenreTag.updateVideoPost(null);
    }


    public static VideoPost createVideoPost(
        User user,
        Video video,
        String title,
        String content
//        String writer
    ){
        VideoPost videopost = new VideoPost();
        videopost.user = user;
        videopost.video = video;
        videopost.title = title;
        videopost.content = content;
//        videopost.writer = writer;

        return videopost;
    }
}
