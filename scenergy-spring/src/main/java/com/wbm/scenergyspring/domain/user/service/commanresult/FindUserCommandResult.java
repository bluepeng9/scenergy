package com.wbm.scenergyspring.domain.user.service.commanresult;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FindUserCommandResult {
	private String nickname;
}
