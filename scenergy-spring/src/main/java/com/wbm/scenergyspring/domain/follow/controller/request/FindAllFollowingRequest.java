package com.wbm.scenergyspring.domain.follow.controller.request;

import com.wbm.scenergyspring.domain.follow.service.command.FindAllFollowingCommand;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FindAllFollowingRequest {
	Long fromUserId;

	public FindAllFollowingCommand getAllFollowing() {
		FindAllFollowingCommand command = FindAllFollowingCommand.builder()
			.userId(getFromUserId())
			.build();
		return command;
	}
}
