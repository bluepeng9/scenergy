package com.wbm.scenergyspring.domain.post.videoPost.entity;

import java.util.ArrayList;
import java.util.List;

import com.wbm.scenergyspring.domain.post.Post;
import com.wbm.scenergyspring.domain.post.videoPost.service.command.UpdatePostVideoCommand;
import com.wbm.scenergyspring.domain.user.entity.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;

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
    ) {
        VideoPost videoPost = new VideoPost();
        videoPost.user = user;
        videoPost.video = video;
        videoPost.updatePost(title, content, writer);
        return videoPost;
    }

    public void updateVideoPostGenreTags(List<VideoPostGenreTag> videoPostGenreTags) {
        this.videoPostGenreTags = videoPostGenreTags;
    }

    public void updateVideoPostInstrumentTags(List<VideoPostInstrumentTag> videoPostInstrumentTags) {
        this.videoPostInstrumentTags = videoPostInstrumentTags;
    }

    public void updateVideoPost(UpdatePostVideoCommand command) {
        if (command.getPostTitle() != null)
            updatePostTitle(command.getPostTitle());
        if (command.getPostContent() != null)
            updatePostContent(command.getPostContent());
        if (command.getPostWriter() != null)
            updatePostWriter(command.getPostWriter());
    }

    public void deleteVideoPostGenreTags() {
        videoPostGenreTags = null;
    }

    public void deleteVideoPostInstrumentTags() {
        videoPostInstrumentTags = null;
    }
}
