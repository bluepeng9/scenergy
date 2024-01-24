package com.wbm.scenergyspring.domain.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wbm.scenergyspring.domain.post.entity.VideoPost;

@Repository
public interface PostRepository extends JpaRepository<VideoPost, Long> {
}
