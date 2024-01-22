package com.wbm.scenergyspring.domain.tag.repository;

import com.wbm.scenergyspring.domain.tag.entity.VideoInstrumentTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoInstrumentTagRepository extends JpaRepository<VideoInstrumentTag,Long> {
}
