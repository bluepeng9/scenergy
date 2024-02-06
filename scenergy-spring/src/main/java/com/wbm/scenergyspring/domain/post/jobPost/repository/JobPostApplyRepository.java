package com.wbm.scenergyspring.domain.post.jobPost.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wbm.scenergyspring.domain.post.jobPost.entity.JobPost;
import com.wbm.scenergyspring.domain.post.jobPost.entity.JobPostApply;
import com.wbm.scenergyspring.domain.user.entity.User;

@Repository
public interface JobPostApplyRepository extends JpaRepository<JobPostApply, Long> {
	JobPostApply findByJobPostAndUser(JobPost jobPost, User user);
	JobPostApply findApplyByJobPost(JobPost jobPost);


}
