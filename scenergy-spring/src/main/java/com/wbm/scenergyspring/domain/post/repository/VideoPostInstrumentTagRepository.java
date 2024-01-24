package com.wbm.scenergyspring.domain.post.repository;

import com.wbm.scenergyspring.domain.post.entity.VideoPostInstrumentTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoPostInstrumentTagRepository extends JpaRepository<VideoPostInstrumentTag, Long> {
}
