package com.wbm.scenergyspring.domain.follow.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wbm.scenergyspring.domain.follow.controller.request.DeleteFollowRequest;
import com.wbm.scenergyspring.domain.follow.controller.request.FindAllFollowersRequest;
import com.wbm.scenergyspring.domain.follow.controller.request.FindAllFollowingRequest;
import com.wbm.scenergyspring.domain.follow.controller.response.CreateFollowResponse;
import com.wbm.scenergyspring.domain.follow.controller.response.DeleteFollowResponse;
import com.wbm.scenergyspring.domain.follow.controller.response.FindAllFollowersResponse;
import com.wbm.scenergyspring.domain.follow.controller.response.FindAllFollowingResponse;
import com.wbm.scenergyspring.domain.follow.entity.Follow;
import com.wbm.scenergyspring.domain.follow.service.FollowService;
import com.wbm.scenergyspring.domain.follow.controller.request.CreateFollowRequest;
import com.wbm.scenergyspring.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/follow")
public class FollowController {

	private final FollowService followService;

	@PostMapping
	public ResponseEntity<ApiResponse<CreateFollowResponse>> createFollow(
		@RequestBody CreateFollowRequest request
	) {
		Long followId = followService.followUser(request.toCreateFollow());
		CreateFollowResponse createFollowResponse = CreateFollowResponse.builder()
			.followId(followId)
			.build();
		return ResponseEntity.ok(ApiResponse.createSuccess(createFollowResponse));
	}

	@DeleteMapping
	public ResponseEntity<ApiResponse<DeleteFollowResponse>> deleteFollow(
		@RequestBody DeleteFollowRequest request
	) {

		Long isSuccess = followService.unFollowUser(request.toDeleteFollow());
		DeleteFollowResponse deleteFollowResponse = DeleteFollowResponse.builder()
			.isSuccess(isSuccess)
			.build();

		return ResponseEntity.ok(ApiResponse.createSuccess(deleteFollowResponse));
	}

	@GetMapping("/followers")
	public ResponseEntity<ApiResponse<FindAllFollowersResponse>> getAllFollowers(
		@RequestBody FindAllFollowersRequest request
	) {
		List<Follow> followers = followService.findAllFollowers(request.getAllFollowers());
		FindAllFollowersResponse findAllFollowersResponse = FindAllFollowersResponse.builder()
			.followers(followers)
			.build();
		return ResponseEntity.ok(ApiResponse.createSuccess(findAllFollowersResponse));
	}

	@GetMapping("/followings")
	public ResponseEntity<ApiResponse<FindAllFollowingResponse>> getAllFollowing(
		@RequestBody FindAllFollowingRequest request
	) {
		List<Follow> following = followService.findAllFollowing(request.getAllFollowing());
		FindAllFollowingResponse findAllFollowingResponse = FindAllFollowingResponse.builder()
			.following(following)
			.build();
		return ResponseEntity.ok(ApiResponse.createSuccess(findAllFollowingResponse));
	}

}
