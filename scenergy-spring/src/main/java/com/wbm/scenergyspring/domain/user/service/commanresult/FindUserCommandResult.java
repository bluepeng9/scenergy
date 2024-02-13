package com.wbm.scenergyspring.domain.user.service.commanresult;

import com.wbm.scenergyspring.domain.user.entity.Gender;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FindUserCommandResult {
	private Long userId;
	private String userPassword;
	private String userName;
	private String userNickname;
	private String url;
	private Gender userGender;
}
