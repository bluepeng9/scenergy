package com.wbm.scenergyspring.domain.tag.repository;

import com.wbm.scenergyspring.domain.tag.entity.GenreTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenreTagRepository extends JpaRepository<GenreTag, Long> {

    Boolean existsByGenreName (String genreName);

    Optional<GenreTag> findByGenreName(String genreName);

}
