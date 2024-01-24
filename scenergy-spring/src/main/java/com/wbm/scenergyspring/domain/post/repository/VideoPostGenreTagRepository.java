package com.wbm.scenergyspring.domain.post.repository;

import com.wbm.scenergyspring.domain.post.entity.VideoPostGenreTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoPostGenreTagRepository extends JpaRepository<VideoPostGenreTag, Long> {
}
