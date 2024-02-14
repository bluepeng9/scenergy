package com.wbm.scenergyspring.domain.follow.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wbm.scenergyspring.domain.follow.entity.Follow;
import com.wbm.scenergyspring.domain.user.entity.User;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

	Long countByTo(User to);

	boolean existsByFromAndTo(User from, User to);

	long deleteByFromAndTo(User from, User to);

	@Query("""
		select f from Follow f
		join fetch f.to
		where  f.to = :to
		""")
	List<Follow> findAllByTo(@Param("to") User to);

	@Query("""
		select f from Follow f
		join fetch f.from
		where f.from = :user
		""")
	List<Follow> findAllByFrom(@Param("user") User user);

	Optional<Follow> findByFromAndTo(User from, User to);
}
