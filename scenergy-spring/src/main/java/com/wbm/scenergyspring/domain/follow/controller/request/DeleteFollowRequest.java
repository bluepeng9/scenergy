package com.wbm.scenergyspring.domain.follow.controller.request;

import com.wbm.scenergyspring.domain.follow.service.command.UnFollowUserCommand;

import lombok.Data;

@Data
public class DeleteFollowRequest {
	Long fromUserId;
	Long toUserId;

	public UnFollowUserCommand toDeleteFollow() {
		UnFollowUserCommand command = UnFollowUserCommand.builder()
			.fromUserId(getFromUserId())
			.toUserId(getToUserId())
			.build();
		return command;
	}
}
