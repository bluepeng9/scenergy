package com.wbm.scenergyspring.domain.portfolio.repository;

import com.wbm.scenergyspring.domain.portfolio.Portfolio;
import com.wbm.scenergyspring.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
}
