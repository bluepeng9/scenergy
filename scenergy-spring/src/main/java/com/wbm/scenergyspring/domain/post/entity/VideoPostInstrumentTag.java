package com.wbm.scenergyspring.domain.post.entity;

import com.wbm.scenergyspring.domain.tag.entity.InstrumentTag;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
public class VideoPostInstrumentTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private VideoPost videoPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private InstrumentTag instrumentTag;

    public void updateVideoPost(VideoPost videoPost) {
        this.videoPost = videoPost;
    }

    public void updateInstrumentTag(InstrumentTag instrumentTag) {
        this.instrumentTag = instrumentTag;
    }

}
