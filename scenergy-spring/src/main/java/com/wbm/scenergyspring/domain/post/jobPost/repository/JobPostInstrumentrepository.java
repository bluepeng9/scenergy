package com.wbm.scenergyspring.domain.post.jobPost.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wbm.scenergyspring.domain.post.jobPost.entity.JobPostInstrumentTag;

public interface JobPostInstrumentrepository extends JpaRepository<JobPostInstrumentTag, Long> {
}
