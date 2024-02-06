package com.wbm.scenergyspring.domain.follow.controller.response;

import com.wbm.scenergyspring.domain.follow.entity.Follow;
import com.wbm.scenergyspring.domain.user.entity.User;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FindAllResponse {
	User from;
	User to;

	public static FindAllResponse from (Follow follow) {
		User from = follow.getFrom();
		User to = follow.getTo();

		return FindAllResponse.builder()
			.from(from)
			.to(to)
			.build();
	}
}

