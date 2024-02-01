package com.wbm.scenergyspring.domain.post.jobPost.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wbm.scenergyspring.domain.post.jobPost.entity.JobPost;

@Repository
public interface JobPostRepository extends JpaRepository<JobPost, Long> {
}
