package com.wbm.scenergyspring.domain.post.videoPost.repository;

import com.wbm.scenergyspring.domain.post.videoPost.entity.VideoPost;
import com.wbm.scenergyspring.domain.post.videoPost.entity.VideoPostGenreTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VideoPostGenreTagRepository extends JpaRepository<VideoPostGenreTag, Long> {

    public Optional<List<VideoPostGenreTag>> findByVideoPost(VideoPost videoPost);

}
