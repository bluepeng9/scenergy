package com.wbm.scenergyspring.domain.user.controller;

import com.wbm.scenergyspring.domain.user.controller.request.CreateUserRequest;
import com.wbm.scenergyspring.domain.user.controller.request.UploadProfileRequest;
import com.wbm.scenergyspring.domain.user.controller.response.CreateUserResponse;
import com.wbm.scenergyspring.domain.user.service.UserService;
import com.wbm.scenergyspring.domain.user.service.command.UploadProfileCommand;
import com.wbm.scenergyspring.domain.user.service.commanresult.FindUserCommandResult;
import com.wbm.scenergyspring.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

	final UserService userService;

	@PostMapping("/profile")
	public ResponseEntity<ApiResponse<String>> uploadProfileS3(@ModelAttribute UploadProfileRequest request) {
		String profileUrlPath = userService.uploadProfileS3(
				UploadProfileCommand.builder()
						.multipartFile(request.getProfile())
						.userId(request.getUserId())
						.build()
		);
		if (profileUrlPath == null)
			return new ResponseEntity<>(ApiResponse.createError("profile error"), HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(ApiResponse.createSuccess(profileUrlPath), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<ApiResponse<CreateUserResponse>> createUser(
		@RequestBody CreateUserRequest request
	) {
		log.info("CreateUserRequest: " + request);
		Long userId = userService.createUser(request.toCreateUserCommand());

		CreateUserResponse createUserResponse = new CreateUserResponse();
		createUserResponse.setUserId(userId);

		return ResponseEntity.ok(ApiResponse.createSuccess(createUserResponse));
	}

	//get User info
	@GetMapping("/{userId}")
	public ResponseEntity<ApiResponse<FindUserCommandResult>> getUser(@PathVariable Long userId) {
		FindUserCommandResult findUser = userService.findUser(userId);
		return ResponseEntity.ok(ApiResponse.createSuccess(findUser));
	}


	@DeleteMapping
	public ResponseEntity<ApiResponse<Long>> deleteUser(
		@RequestParam("password") String password,
		@RequestParam("username") String username
	) {
		Long delUser = userService.deleteUser(password,username);
		return ResponseEntity.ok(ApiResponse.createSuccess(delUser));
	}
}
