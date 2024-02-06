package com.wbm.scenergyspring.domain.follow.controller.request;

import com.wbm.scenergyspring.domain.follow.service.command.FollowUserCommand;

import lombok.Data;

@Data
public class CreateFollowRequest {
	Long fromUserId;
	Long toUserId;

	public FollowUserCommand toCreateFollow() {
		FollowUserCommand command = FollowUserCommand.builder()
			.fromUserId(getFromUserId())
			.toUserId(getToUserId())
			.build();

		return command;
	}
}
