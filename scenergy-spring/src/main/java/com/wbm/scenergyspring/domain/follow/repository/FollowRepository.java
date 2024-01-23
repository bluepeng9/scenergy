package com.wbm.scenergyspring.domain.follow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wbm.scenergyspring.domain.follow.entity.Follow;
import com.wbm.scenergyspring.domain.user.entity.User;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

	boolean existsByFromAndTo(User from, User to);

	long deleteByFromAndTo(User from, User to);
}
