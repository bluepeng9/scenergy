package com.wbm.scenergyspring.domain.follow.controller.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateFollowResponse {
	Long followId;
	Long fromUserId;
	Long toUserId;
}
