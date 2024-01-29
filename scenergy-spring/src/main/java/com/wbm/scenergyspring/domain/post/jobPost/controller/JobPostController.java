package com.wbm.scenergyspring.domain.post.jobPost.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wbm.scenergyspring.domain.post.jobPost.controller.request.CreateJobPostRequest;
import com.wbm.scenergyspring.domain.post.jobPost.controller.request.DeleteJobPostRequest;
import com.wbm.scenergyspring.domain.post.jobPost.controller.request.UpdateJobPostRequest;
import com.wbm.scenergyspring.domain.post.jobPost.controller.response.CreateJobPostResponse;
import com.wbm.scenergyspring.domain.post.jobPost.controller.response.DeleteJobPostResponse;
import com.wbm.scenergyspring.domain.post.jobPost.controller.response.UpdateJobPostResponse;
import com.wbm.scenergyspring.domain.post.jobPost.service.JobPostService;
import com.wbm.scenergyspring.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jobPost")

public class JobPostController {

	final JobPostService jobPostService;

	@PostMapping
	public ResponseEntity<ApiResponse<CreateJobPostResponse>> createJobPost(
		@RequestBody CreateJobPostRequest request
		) {
			Long jobPostId = jobPostService.createJobPost(request.toCreateJobPost());

			CreateJobPostResponse createJobPostResponse = new CreateJobPostResponse();
			createJobPostResponse.setJobPostId(jobPostId);

			return ResponseEntity.ok(ApiResponse.createSuccess(createJobPostResponse));
	}

	@PutMapping
	public ResponseEntity<ApiResponse<UpdateJobPostResponse>> updateJobPost(
		@RequestBody UpdateJobPostRequest request
	) {
		Long jobPostId = jobPostService.updateJobPost(request.toUpdateJobPost());

		UpdateJobPostResponse updateJobPostRespon = new UpdateJobPostResponse();
		updateJobPostResponse.setJobPostId(jobPostId);

		return ResponseEntity.ok(ApiResponse.createSuccess(updateJobPostResponse));
	}

	@DeleteMapping
	public ResponseEntity<ApiResponse<DeleteJobPostResponse>> deleteJobPost(
		@RequestBody DeleteJobPostRequest request
	) {
		Long jobPostId = jobPostService.deleteJobPost(request.toDeleteJobPost());

		DeleteJobPostResponse deleteJobPostResponse = new DeleteJobPostResponse();
		deleteJobPostResponse.setJobPostId(jobPostId);

		return ResponseEntity.ok(ApiResponse.createSuccess(deleteJobPostResponse));
	}
}
