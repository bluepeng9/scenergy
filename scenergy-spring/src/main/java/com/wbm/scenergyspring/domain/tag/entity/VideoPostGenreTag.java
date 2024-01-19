package com.wbm.scenergyspring.domain.tag.entity;

import com.wbm.scenergyspring.domain.post.entity.VideoPost;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class VideoPostGenreTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="video_post_id")
    private VideoPost videoPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="genre_tag_id")
    private GenreTag genreTag;

    public void updateVideoPost(VideoPost videoPost){
        this.videoPost=videoPost;
    }

    public void updateGenreTag(GenreTag genreTag){
        this.genreTag=genreTag;
    }
}
