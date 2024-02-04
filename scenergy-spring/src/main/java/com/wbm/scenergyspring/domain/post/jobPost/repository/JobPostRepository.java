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


	@Query("SELECT ja.jobPost FROM JobPostApply ja "
		+ "WHERE ja.user.id = :userId AND ja.status = true")
	List<JobPost> findAllApplyPostByUser(Long userId);

	@Query("select jp From JobPost jp "
	+ "where jp.id in (select jb.jobPost.id from JobBookMark jb where jb.user.id=:id)")
	List<JobPost> findAllBookMark(Long id);
}
