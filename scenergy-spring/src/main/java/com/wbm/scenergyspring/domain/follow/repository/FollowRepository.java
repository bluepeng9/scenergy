package com.wbm.scenergyspring.domain.follow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wbm.scenergyspring.domain.follow.entity.Follow;
import com.wbm.scenergyspring.domain.user.entity.User;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

	boolean existsByFromAndTo(User from, User to);

	long deleteByFromAndTo(User from, User to);

	@Query("""
		select f from Follow f
		join fetch f.to
		""")
	List<Follow> findAllByTo(User to);

	@Query("""
		select f from Follow f
		join fetch f.from
		""")
	List<Follow> findAllByFrom(User from);
}
