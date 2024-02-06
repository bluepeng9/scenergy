package com.wbm.scenergyspring.domain.post.videoPost.repository;

import com.wbm.scenergyspring.domain.post.videoPost.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
}
