package com.wbm.scenergyspring.domain.post.repository;

import com.wbm.scenergyspring.domain.post.entity.VideoPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoPostRepository extends JpaRepository<VideoPost, Long> {
}
