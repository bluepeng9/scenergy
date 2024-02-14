package com.wbm.scenergyspring.domain.follow.service.commandresult;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FollowCommandResult {
	Long followId;
	Long fromUserId;
	Long toUserId;
	Long userFollowerCount;
}
