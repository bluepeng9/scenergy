package com.wbm.scenergyspring.domain.tag.controller.request;

import com.wbm.scenergyspring.domain.tag.service.command.LocationTagCommand;

import lombok.Data;

@Data
public class UpdateLocationTagRequest {

	private String locationName;
	private String changeLocationName;

	public LocationTagCommand toLocationTag() {
		LocationTagCommand command = LocationTagCommand.builder()
			.locationTagName(locationName)
			.changeLocationTagName(changeLocationName)
			.build();
		return command;
	}
}
