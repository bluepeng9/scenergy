package com.wbm.scenergyspring.domain.tag.repository;

import com.wbm.scenergyspring.domain.tag.entity.InstrumentTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstrumentTagRepository extends JpaRepository<InstrumentTag,Long> {

    Boolean existsByInstrumentName (String InstrumentName);

}
