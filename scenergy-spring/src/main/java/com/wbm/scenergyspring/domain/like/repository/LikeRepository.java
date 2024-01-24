package com.wbm.scenergyspring.domain.like.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wbm.scenergyspring.domain.like.Like;
import com.wbm.scenergyspring.domain.post.entity.VideoPost;
import com.wbm.scenergyspring.domain.user.entity.User;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

	Optional<Like> findByVideoPostAndUser(VideoPost videoPost, User user);
}
