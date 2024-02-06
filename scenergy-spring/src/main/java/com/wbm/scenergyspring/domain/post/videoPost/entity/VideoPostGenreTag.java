package com.wbm.scenergyspring.domain.post.videoPost.entity;

import com.wbm.scenergyspring.domain.tag.entity.GenreTag;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
public class VideoPostGenreTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
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
