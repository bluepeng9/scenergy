package com.wbm.scenergyspring.domain.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wbm.scenergyspring.domain.user.controller.request.CreateUserRequest;
import com.wbm.scenergyspring.domain.user.controller.response.CreateUserResponse;
import com.wbm.scenergyspring.domain.user.service.UserService;
import com.wbm.scenergyspring.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

	final UserService userService;

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


	@DeleteMapping
	public ResponseEntity<ApiResponse<Long>> deleteUser(
		@RequestParam("password") String password,
		@RequestParam("username") String username
	) {
		Long delUser = userService.deleteUser(password,username);
		return ResponseEntity.ok(ApiResponse.createSuccess(delUser));
	}
}
