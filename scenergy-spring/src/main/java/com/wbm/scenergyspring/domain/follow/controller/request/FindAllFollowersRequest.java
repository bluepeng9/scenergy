package com.wbm.scenergyspring.domain.follow.controller.request;

import com.wbm.scenergyspring.domain.follow.service.command.FindAllFollowersCommand;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FindAllFollowersRequest {
	Long toUserId;

	public FindAllFollowersCommand getAllFollowers() {
		FindAllFollowersCommand command  = FindAllFollowersCommand.builder()
			.userId(getToUserId())
			.build();
		return command;
	}
}
