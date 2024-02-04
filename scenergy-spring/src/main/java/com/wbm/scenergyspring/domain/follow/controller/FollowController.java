package com.wbm.scenergyspring.domain.follow.controller;

import java.util.ArrayList;
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
import com.wbm.scenergyspring.domain.follow.controller.response.FindAllResponse;
import com.wbm.scenergyspring.domain.follow.entity.Follow;
import com.wbm.scenergyspring.domain.follow.service.FollowService;
import com.wbm.scenergyspring.domain.follow.controller.request.CreateFollowRequest;
import com.wbm.scenergyspring.domain.follow.service.commandresult.FollowCommandResult;
import com.wbm.scenergyspring.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/follow")
public class FollowController {

	private final FollowService followService;

	@PostMapping
	public ResponseEntity<ApiResponse<FollowCommandResult>> createFollow(
		@RequestBody CreateFollowRequest request
	) {
		FollowCommandResult commandResult = followService.followUser(request.toCreateFollow());
		return ResponseEntity.ok(ApiResponse.createSuccess(commandResult));
	}

	@DeleteMapping
	public ResponseEntity<ApiResponse<DeleteFollowResponse>> deleteFollow(
		@RequestBody DeleteFollowRequest request
	) {

		Long success = followService.unFollowUser(request.toDeleteFollow());
		boolean isSuccess;
		isSuccess = success == 1;
		DeleteFollowResponse deleteFollowResponse = DeleteFollowResponse.builder()
			.isSuccess(isSuccess)
			.build();

		return ResponseEntity.ok(ApiResponse.createSuccess(deleteFollowResponse));
	}



	@GetMapping("/followers")
	public ResponseEntity<ApiResponse<List<FindAllResponse>>> getAllFollowers(
		@RequestBody FindAllFollowersRequest request
	) {
		List<Follow> followers = followService.findAllFollowers(request.getAllFollowers());
		List<FindAllResponse> followersResponseList = new ArrayList<>();

		for (Follow follow : followers) {
			FindAllResponse followersResponse = FindAllResponse.builder()
				.from(follow.getFrom())
				.to(follow.getTo())
				.build();
			followersResponseList.add(followersResponse);
		}

		return ResponseEntity.ok(ApiResponse.createSuccess(followersResponseList));
	}

	@GetMapping("/followings")
	public ResponseEntity<ApiResponse<List<FindAllResponse>>> getAllFollowing(
		@RequestBody FindAllFollowingRequest request
	) {
		List<Follow> followings = followService.findAllFollowing(request.getAllFollowing());
		List<FindAllResponse> followingResponseList = new ArrayList<>();
		for (Follow following : followings) {
			FindAllResponse followingResponse = FindAllResponse.builder()
				.from(following.getFrom())
				.to(following.getTo())
				.build();
			followingResponseList.add(followingResponse);
		}
		return ResponseEntity.ok(ApiResponse.createSuccess(followingResponseList));
	}

}
