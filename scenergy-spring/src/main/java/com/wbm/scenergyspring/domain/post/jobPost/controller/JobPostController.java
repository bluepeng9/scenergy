package com.wbm.scenergyspring.domain.post.jobPost.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wbm.scenergyspring.domain.post.jobPost.controller.request.CreateJobPostRequest;
import com.wbm.scenergyspring.domain.post.jobPost.controller.request.DeleteJobPostRequest;
import com.wbm.scenergyspring.domain.post.jobPost.controller.request.GetJobPostRequest;
import com.wbm.scenergyspring.domain.post.jobPost.controller.request.UpdateJobPostRequest;
import com.wbm.scenergyspring.domain.post.jobPost.controller.response.CreateJobPostResponse;
import com.wbm.scenergyspring.domain.post.jobPost.controller.response.DeleteJobPostResponse;
import com.wbm.scenergyspring.domain.post.jobPost.controller.response.GetJobPostCommandResponse;
import com.wbm.scenergyspring.domain.post.jobPost.service.JobPostService;
import com.wbm.scenergyspring.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jobPost")

public class JobPostController {

	final JobPostService jobPostService;

	@GetMapping("/getAll")
	public ResponseEntity<ApiResponse<List<GetJobPostCommandResponse>>> getAllJobPost() {
		return new ResponseEntity<>(ApiResponse.createSuccess(jobPostService.getAllJobPostList()), HttpStatus.OK);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<ApiResponse<List<GetJobPostCommandResponse>>> getAllMyJobPost(
		@PathVariable Long id
	) {
		return new ResponseEntity<>(ApiResponse.createSuccess(jobPostService.getMyJobPost(id)),HttpStatus.OK);
	}

	@GetMapping("/get")
	public ResponseEntity<ApiResponse<GetJobPostCommandResponse>> getJobPost(
		@RequestBody GetJobPostRequest request
	) {
		GetJobPostCommandResponse getJobPostCommandResponse = jobPostService.getJobPost(request.toGetJobPost());
		return ResponseEntity.ok(ApiResponse.createSuccess(getJobPostCommandResponse));
	}

	@PostMapping
	public ResponseEntity<ApiResponse<CreateJobPostResponse>> createJobPost(
		@RequestBody CreateJobPostRequest request
		) {

			Long jobPostId = jobPostService.createJobPost(request.toCreateJobPost());

			CreateJobPostResponse createJobPostResponse = new CreateJobPostResponse();
			createJobPostResponse.setJobPostId(jobPostId);

			return ResponseEntity.ok(ApiResponse.createSuccess(createJobPostResponse));
	}

	@PutMapping("{id}")
	public ResponseEntity<ApiResponse<String>> updateJobPost(
		@PathVariable Long id,  @RequestBody UpdateJobPostRequest request
	) {
		jobPostService.updateJobPost(id, request);
		return new ResponseEntity<>(ApiResponse.createSuccess("success"), HttpStatus.OK);
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
