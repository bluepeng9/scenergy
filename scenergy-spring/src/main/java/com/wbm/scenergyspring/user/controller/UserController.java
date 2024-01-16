package com.wbm.scenergyspring.user.controller;

import com.wbm.scenergyspring.user.controller.request.CreateUserRequest;
import com.wbm.scenergyspring.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    final UserService userService;

    @PostMapping
    public void createUser(CreateUserRequest request) {
        userService.createUser(request.toCreateUserCommand());
    }
}
