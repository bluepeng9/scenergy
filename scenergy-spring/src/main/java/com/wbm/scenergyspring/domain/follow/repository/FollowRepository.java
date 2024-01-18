package com.wbm.scenergyspring.domain.follow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wbm.scenergyspring.domain.follow.entity.Follow;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
}
