package com.wbm.scenergyspring.domain.post.jobPost.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wbm.scenergyspring.domain.post.jobPost.entity.JobPost;

public interface JobPostRepository extends JpaRepository<JobPost, Long> {
}
