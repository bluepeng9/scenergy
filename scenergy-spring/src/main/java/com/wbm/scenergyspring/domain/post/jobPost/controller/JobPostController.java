package com.wbm.scenergyspring.domain.post.jobPost.controller;

import com.wbm.scenergyspring.domain.post.jobPost.controller.request.*;
import com.wbm.scenergyspring.domain.post.jobPost.controller.response.*;
import com.wbm.scenergyspring.domain.post.jobPost.service.Command.CancleApplicationCommand;
import com.wbm.scenergyspring.domain.post.jobPost.service.Command.DeleteBookMarkCommand;
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
		List<GetJobPostCommandResponse> getJobPostCommandResponsesList = jobPostService.getAllJobPostList();
		// List<GetJobPostControllerResponse> responses = new ArrayList<>();
		// for (GetJobPostCommandResponse getJobPostCommandResponse: getJobPostCommandResponsesList) {
		// 	GetJobPostControllerResponse tmpResponse = GetJobPostControllerResponse.from(getJobPostCommandResponse);
		// 	responses.add(tmpResponse);
		// }
		// return new ResponseEntity<>(ApiResponse.createSuccess(responses),HttpStatus.OK);
		return new ResponseEntity<>(ApiResponse.createSuccess(getJobPostCommandResponsesList),HttpStatus.OK);
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

	@GetMapping("/get/myJobPost/{id}")
	public ResponseEntity<ApiResponse<List<GetJobPostCommandResponse>>> getAllMyJobPost(
		@PathVariable Long id
	) {
		return new ResponseEntity<>(ApiResponse.createSuccess(jobPostService.getMyJobPost(id)),HttpStatus.OK);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<ApiResponse<GetJobPostCommandResponse>> getJobPost(
		@PathVariable Long id
	) {
		GetJobPostRequest request = GetJobPostRequest.builder()
			.jobPostId(id)
			.build();
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

	@DeleteMapping
	public ResponseEntity<ApiResponse<CancleApplicationResponse>> cancleApplication(
		@RequestParam Long jobPostId,
		@RequestParam Long userId
	) {
		CancleApplicationCommand command = CancleApplicationCommand.builder()
			.jobPostId(jobPostId)
			.userId(userId)
			.build();
		jobPostService.cancleApplication(command);
		CancleApplicationResponse cancleApplicationResponse = CancleApplicationResponse.builder()
			.isSuccess(true)
			.build();
		return ResponseEntity.ok(ApiResponse.createSuccess(cancleApplicationResponse));
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

	@DeleteMapping("/bookmark/delete")
	public ResponseEntity<ApiResponse<JobPostDeleteBookMarkResponse>> deleteBookMarkJobPost(
		@RequestParam Long jobPostId,
		@RequestParam Long userId
	) {
		DeleteBookMarkCommand command = DeleteBookMarkCommand.builder()
			.jobPostId(jobPostId)
			.userId(userId)
			.build();
		jobPostService.deleteBookMarkJobPost(command);
		JobPostDeleteBookMarkResponse deleteBookMarkResponse = JobPostDeleteBookMarkResponse.builder()
			.isSuccess(true)
			.build();
		return ResponseEntity.ok(ApiResponse.createSuccess(deleteBookMarkResponse));
	}

	@PostMapping("/create")
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

	@DeleteMapping("{id}")
	public ResponseEntity<ApiResponse<DeleteJobPostResponse>> deleteJobPost(
		@PathVariable Long id
	) {
		DeleteJobPostRequest request = DeleteJobPostRequest.builder()
			.jobPostId(id)
			.build();
		Long jobPostId1 = jobPostService.deleteJobPost(request.toDeleteJobPost());

		DeleteJobPostResponse deleteJobPostResponse = new DeleteJobPostResponse();
		deleteJobPostResponse.setJobPostId(jobPostId1);

		return ResponseEntity.ok(ApiResponse.createSuccess(deleteJobPostResponse));
	}

	@PostMapping("/search")
	public ResponseEntity<ApiResponse<List<GetJobPostCommandResponse>>> searchJobPost(
			@RequestBody SearchAllJobPostRequest request
	) {
		SearchAllJobPostCommand command = request.toSearchAllJobPostCommand();
		List<GetJobPostCommandResponse> list = jobPostService.searchAllJobPost(command);

		return ResponseEntity.ok(ApiResponse.createSuccess(list));
	}
}
