package com.wbm.scenergyspring.domain.tag.repository;

import java.util.Optional;

import com.wbm.scenergyspring.domain.tag.entity.InstrumentTag;
import com.wbm.scenergyspring.domain.tag.entity.LocationTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationTagRepository extends JpaRepository<LocationTag,Long> {

    Boolean existsByLocationName (String locationName);

    Optional<LocationTag> findByLocationName(String locationName);

}
