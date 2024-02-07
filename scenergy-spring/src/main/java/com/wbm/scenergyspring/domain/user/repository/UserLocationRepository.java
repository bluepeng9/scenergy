package com.wbm.scenergyspring.domain.user.repository;

import com.wbm.scenergyspring.domain.user.entity.UserLocationTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLocationRepository extends JpaRepository<UserLocationTag, Long> {
}
