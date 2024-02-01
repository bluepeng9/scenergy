package com.wbm.scenergyspring.domain.post.videoPost.repository;

import com.wbm.scenergyspring.domain.post.videoPost.entity.VideoPost;
import com.wbm.scenergyspring.domain.post.videoPost.entity.VideoPostInstrumentTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VideoPostInstrumentTagRepository extends JpaRepository<VideoPostInstrumentTag, Long> {

    public Optional<List<VideoPostInstrumentTag>> findByVideoPost(VideoPost videoPost);

}
