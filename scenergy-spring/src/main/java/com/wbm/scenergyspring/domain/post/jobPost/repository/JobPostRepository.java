package com.wbm.scenergyspring.domain.post.jobPost.repository;

import com.wbm.scenergyspring.domain.post.jobPost.entity.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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

	@Query("""
				SELECT DISTINCT jp
				FROM JobPost jp
				LEFT JOIN jp.jobPostGenreTags gt
				LEFT JOIN jp.jobPostInstrumentTags it
				LEFT JOIN jp.jobPostLocationTags lt
				WHERE (jp.title LIKE %:name% OR :name IS NULL)
				AND (gt.genreTag.id in :gt OR :gt is null)
				AND (it.instrumentTag.id in :it OR :it is null)
				AND (lt.locationTag.id in :lt OR :lt is null)
			""")
	List<JobPost> searchAllJobPost(
			@Param("name") String name,
			@Param("gt") List<Long> gt,
			@Param("it") List<Long> it,
			@Param("lt") List<Long> lt
	);

}
