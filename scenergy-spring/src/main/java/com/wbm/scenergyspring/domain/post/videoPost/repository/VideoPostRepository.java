package com.wbm.scenergyspring.domain.post.videoPost.repository;

import com.wbm.scenergyspring.domain.post.videoPost.entity.VideoPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoPostRepository extends JpaRepository<VideoPost, Long> {
    @Query("""
            select distinct vp from VideoPost vp
            left join vp.user u
            left join UserLocationTag ut
            on u = ut.user
            join vp.videoPostGenreTags genre
            join vp.videoPostInstrumentTags instru
            """)
    List<VideoPost> searchVideoPostsByCondition(@Param("word") String word, @Param("gt") List<Long> gt, @Param("it") List<Long> it, @Param("lt") List<Long> lt);


    @Query("select vp from VideoPost vp " +
            "where vp.user.id in (select f.to.id from Follow f where f.from.id=:id)")
    List<VideoPost> findAllByFollowing(@Param("id") Long id);

}