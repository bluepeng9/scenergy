package com.wbm.scenergyspring.domain.follow.controller.request;

import com.wbm.scenergyspring.domain.follow.service.command.FindAllFollowingCommand;

import lombok.Data;

@Data
public class FindAllFollowingRequest {
	Long fromUserId;

	public FindAllFollowingCommand getAllFollowing() {
		FindAllFollowingCommand command = FindAllFollowingCommand.builder()
			.fromUserId(getFromUserId())
			.build();
		return command;
	}
}
