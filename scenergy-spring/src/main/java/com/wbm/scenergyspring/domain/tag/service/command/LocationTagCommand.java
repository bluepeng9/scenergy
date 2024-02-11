package com.wbm.scenergyspring.domain.tag.service.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LocationTagCommand {
	private String locationTagName;
	private String changeLocationTagName;
}
