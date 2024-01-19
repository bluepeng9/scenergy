package com.wbm.scenergyspring.domain.portfolio.repository;

import com.wbm.scenergyspring.domain.portfolio.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    @Query("select distinct p from Portfolio p " +
            "left join p.educations  " +
            "left join p.experiences  " +
            "left join p.honors  " +
            "left join p.etcs " +
            "where p.id = :pid")
    Optional<Portfolio> findByIdJoin(@Param("pid") Long pid);
}
