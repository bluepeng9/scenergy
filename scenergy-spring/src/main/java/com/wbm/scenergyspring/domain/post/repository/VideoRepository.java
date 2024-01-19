package com.wbm.scenergyspring.domain.post.repository;

import com.wbm.scenergyspring.domain.post.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
}
