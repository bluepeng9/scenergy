package com.example.scenergynotification.domain.user.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.scenergynotification.domain.user.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	public User findUser(Long userId) {

		FindUserDto findUser = new RestTemplate().getForObject(
			"http://localhost:8080/users/" + userId,
			FindUserDto.class
		);

		return User.createUser(
			findUser.data.getId(),
			findUser.data.getNickname()
		);
	}

}
