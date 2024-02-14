package com.example.scenergynotification.domain.user.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.scenergynotification.domain.user.entity.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

	@Value("${spring.user-service.url}")
	private String userServiceUrl;

	public User findUser(Long userId) {

		FindUserDto findUser = new RestTemplate().getForObject(
			userServiceUrl + "/users/" + userId,
			FindUserDto.class
		);
		log.debug("findUser: " + findUser);

		return User.createUser(
			findUser.data.getUserId(),
			findUser.data.getUserNickname()
		);
	}

}
