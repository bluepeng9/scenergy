package com.example.scenergynotification.domain.user.service;

import lombok.Data;

@Data
public class FindUserDto {

	UserDto data;
}

@Data
class UserDto {
	private Long userId;
	private String userPassword;
	private String userName;
	private String userNickname;
	private String url;
	private String userGender;
}
