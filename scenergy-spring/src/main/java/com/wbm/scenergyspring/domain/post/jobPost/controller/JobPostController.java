package com.wbm.scenergyspring.domain.post.jobPost.controller;

import com.wbm.scenergyspring.domain.post.jobPost.controller.request.*;
import com.wbm.scenergyspring.domain.post.jobPost.controller.response.*;
import com.wbm.scenergyspring.domain.post.jobPost.service.Command.SearchAllJobPostCommand;
import com.wbm.scenergyspring.domain.post.jobPost.service.JobPostService;
import com.wbm.scenergyspring.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jobPost")

public class JobPostController {

	final JobPostService jobPostService;

	@GetMapping("/getAll")
	public ResponseEntity<ApiResponse<List<GetJobPostCommandResponse>>> getAllJobPost() {
		return new ResponseEntity<>(ApiResponse.createSuccess(jobPostService.getAllJobPostList()), HttpStatus.OK);
	}

	@GetMapping("get/apply/{id}")
	public ResponseEntity<ApiResponse<List<GetJobPostCommandResponse>>> getAllMyApply(
		@PathVariable Long id
	) {
		return new ResponseEntity<>(ApiResponse.createSuccess(jobPostService.getAllMyApply(id)),HttpStatus.OK);
	}

	@GetMapping("get/bookmark/{id}")
	public ResponseEntity<ApiResponse<List<GetJobPostCommandResponse>>> getAllBookMark(
		@PathVariable Long id
	) {
		return new ResponseEntity<>(ApiResponse.createSuccess(jobPostService.getBookMarkJobPost(id)),HttpStatus.OK);
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

	@PostMapping("/apply")
	public ResponseEntity<ApiResponse<ApplyJobPostReponse>> applyJobPost(
		@RequestBody ApplyJobPostRequest request
	) {
		jobPostService.ApplyJobPost(request.toApplyJobPost());
		ApplyJobPostReponse applyJobPostReponse = ApplyJobPostReponse.builder()
			.isSuccess(true)
			.build();
		return ResponseEntity.ok(ApiResponse.createSuccess(applyJobPostReponse));
	}

	@PostMapping("/bookmark")
	public ResponseEntity<ApiResponse<JobPostBookMarkResponse>> bookMarkJobPost(
		@RequestBody JobPostBookMarkRequest request
	) {
		jobPostService.BookMarkJobPost(request.bookMarkJobPost());
		JobPostBookMarkResponse bookMarkResponse = JobPostBookMarkResponse.builder()
			.isSuccess(true)
			.build();
		return ResponseEntity.ok(ApiResponse.createSuccess(bookMarkResponse));
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

	@PostMapping("/search")
	public ResponseEntity<ApiResponse<List<SearchAllJobPostResponse>>> searchJobPost(
			@RequestBody SearchAllJobPostRequest request
	) {
		SearchAllJobPostCommand command = request.toSearchAllJobPostCommand();
		List<SearchAllJobPostResponse> list = jobPostService.searchAllJobPost(command);

		return ResponseEntity.ok(ApiResponse.createSuccess(list));
	}
}
