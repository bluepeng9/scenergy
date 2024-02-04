package com.wbm.scenergyspring.domain.post.jobPost.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wbm.scenergyspring.domain.post.jobPost.entity.JobPost;

@Repository
public interface JobPostRepository extends JpaRepository<JobPost, Long> {
	@Query("select jp from JobPost jp " +
		"where jp.userId.id in (select u.id from User u where u.id=:id)")
	List<JobPost> findAllByPost(Long id);
}
