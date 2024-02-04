package com.wbm.scenergyspring.domain.post.jobPost.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wbm.scenergyspring.domain.post.jobPost.entity.JobBookMark;
import com.wbm.scenergyspring.domain.post.jobPost.entity.JobPost;
import com.wbm.scenergyspring.domain.user.entity.User;

@Repository
public interface JobBookMarkRepository extends JpaRepository<JobBookMark, Long> {
	JobBookMark findByJobPostAndUser(JobPost jobPost, User user);
	JobBookMark findBookMarkByJobPost(JobPost jobPost);
}
