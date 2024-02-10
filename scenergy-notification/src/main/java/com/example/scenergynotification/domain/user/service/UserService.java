package com.example.scenergynotification.domain.user.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.scenergynotification.domain.user.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	@Value("${spring.user-service.url}")
	private String userServiceUrl;

	public User findUser(Long userId) {

		FindUserDto findUser = new RestTemplate().getForObject(
			userServiceUrl + "/users/" + userId,
			FindUserDto.class
		);

		return User.createUser(
			findUser.data.getId(),
			findUser.data.getNickname()
		);
	}

}
