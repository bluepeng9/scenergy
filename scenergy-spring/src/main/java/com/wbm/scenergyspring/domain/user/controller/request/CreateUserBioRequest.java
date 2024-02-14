package com.wbm.scenergyspring.domain.user.controller.request;

import com.wbm.scenergyspring.domain.user.service.command.UploadBioCommand;

import lombok.Data;

@Data
public class CreateUserBioRequest {
	Long userId;
	String bio;

	public UploadBioCommand toCreateUserBioCommand() {
		return UploadBioCommand.builder()
			.userId(userId)
			.bio(bio)
			.build();
	}
}
