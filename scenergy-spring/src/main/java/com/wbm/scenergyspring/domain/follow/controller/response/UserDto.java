package com.wbm.scenergyspring.domain.follow.controller.response;

import com.wbm.scenergyspring.domain.user.entity.User;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
	private Long id;
	private String nickname;
	private String url;

	static UserDto fromUser(User user) {
		return UserDto.builder()
			.id(user.getId())
			.nickname(user.getNickname())
			.url(user.getUrl())
			.build();
	}
}
