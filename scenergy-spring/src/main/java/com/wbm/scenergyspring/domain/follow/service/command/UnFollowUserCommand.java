package com.wbm.scenergyspring.domain.follow.service.command;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UnFollowUserCommand {
	Long fromUserId;
	Long toUserId;
}