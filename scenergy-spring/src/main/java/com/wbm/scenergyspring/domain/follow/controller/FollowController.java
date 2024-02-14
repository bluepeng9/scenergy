package com.wbm.scenergyspring.domain.follow.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wbm.scenergyspring.config.auth.PrincipalDetails;
import com.wbm.scenergyspring.domain.follow.controller.request.CreateFollowRequest;
import com.wbm.scenergyspring.domain.follow.controller.request.FindAllFollowersRequest;
import com.wbm.scenergyspring.domain.follow.controller.request.FindAllFollowingRequest;
import com.wbm.scenergyspring.domain.follow.controller.response.DeleteFollowResponse;
import com.wbm.scenergyspring.domain.follow.controller.response.FindAllFollowerResponse;
import com.wbm.scenergyspring.domain.follow.controller.response.FindAllFollowingResponse;
import com.wbm.scenergyspring.domain.follow.entity.Follow;
import com.wbm.scenergyspring.domain.follow.service.FollowService;
import com.wbm.scenergyspring.domain.follow.service.command.UnFollowUserCommand;
import com.wbm.scenergyspring.domain.follow.service.commandresult.FollowCommandResult;
import com.wbm.scenergyspring.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/follow")
public class FollowController {

	private final FollowService followService;
	private final KafkaTemplate<String, Object> kafkaTemplate;

	@PostMapping
	public ResponseEntity<ApiResponse<FollowCommandResult>> createFollow(
		@RequestBody CreateFollowRequest request,
		@AuthenticationPrincipal PrincipalDetails principalDetails
	) {
		request.setFromUserId(principalDetails.getUser().getId());

		FollowCommandResult commandResult = followService.followUser(request.toCreateFollow());
		kafkaTemplate.send("follow", commandResult);
		return ResponseEntity.ok(ApiResponse.createSuccess(commandResult));
	}

	@DeleteMapping
	public ResponseEntity<ApiResponse<DeleteFollowResponse>> unFollow(
		@RequestParam("toUserId") Long toUserId,
		@AuthenticationPrincipal PrincipalDetails principalDetails
	) {

		UnFollowUserCommand unFollowUserCommand = UnFollowUserCommand.builder()
			.toUserId(toUserId)
			.fromUserId(principalDetails.getUser().getId())
			.build();

		Long countFollowUser = followService.unFollowUser(unFollowUserCommand);

		DeleteFollowResponse deleteFollowResponse = DeleteFollowResponse.builder()
			.countFollowUser(countFollowUser)
			.build();

		return ResponseEntity.ok(ApiResponse.createSuccess(deleteFollowResponse));
	}

	@GetMapping("/followers/{toUserId}")
	public ResponseEntity<ApiResponse<FindAllFollowerResponse>> getAllFollowers(
		@PathVariable("toUserId") Long toUserId
	) {
		FindAllFollowersRequest request = FindAllFollowersRequest.builder()
			.toUserId(toUserId)
			.build();
		List<Follow> followers = followService.findAllFollowers(request.getAllFollowers());
		FindAllFollowerResponse followersResponse = FindAllFollowerResponse.fromList(followers);
		return ResponseEntity.ok(ApiResponse.createSuccess(followersResponse));
	}

	@GetMapping("/followings/{fromUserId}")
	public ResponseEntity<ApiResponse<FindAllFollowingResponse>> getAllFollowing(
		@PathVariable("fromUserId") Long fromUserId
	) {
		FindAllFollowingRequest request = FindAllFollowingRequest.builder()
			.fromUserId(fromUserId)
			.build();
		List<Follow> followings = followService.findAllFollowing(request.getAllFollowing());
		FindAllFollowingResponse followingResponse = FindAllFollowingResponse.fromList(followings);
		return ResponseEntity.ok(ApiResponse.createSuccess(followingResponse));
	}

	@GetMapping
	public ResponseEntity<ApiResponse<Boolean>> isFollow(
		@RequestParam("fromUserId") Long fromUserId,
		@RequestParam("toUserId") Long toUserId
	) {
		Optional<Follow> follow = followService.getFollow(fromUserId, toUserId);
		return ResponseEntity.ok(ApiResponse.createSuccess(follow.isPresent()));
	}
}
