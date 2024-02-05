package com.wbm.scenergyspring.domain.post.videoPost.repository;

import com.wbm.scenergyspring.domain.post.videoPost.entity.VideoPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoPostRepository extends JpaRepository<VideoPost, Long> {

    @Query("select vp from VideoPost vp " +
            "where vp.user.id in (select f.to.id from Follow f where f.from.id=:id)")
    List<VideoPost> findAllByFollowing(@Param("id") Long id);
}
