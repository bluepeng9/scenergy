package com.example.scenergynotification.domain.user.entity;

import lombok.Data;

@Data
public class User {

	private Long id;
	private String nickname;

	public static User createUser(
		Long id,
		String nickname
	) {
		User user = new User();
		user.id = id;
		user.nickname = nickname;
		return user;
	}
}
