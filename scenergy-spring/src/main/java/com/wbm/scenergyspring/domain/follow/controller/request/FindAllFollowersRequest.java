package com.wbm.scenergyspring.domain.follow.controller.request;

import com.wbm.scenergyspring.domain.follow.service.command.FindAllFollowersCommand;

import lombok.Data;

@Data
public class FindAllFollowersRequest {
	Long toUserId;

	public FindAllFollowersCommand getAllFollowers() {
		FindAllFollowersCommand command  = FindAllFollowersCommand.builder()
			.toUserId(getToUserId())
			.build();
		return command;
	}
}
