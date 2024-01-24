package com.wbm.scenergyspring.domain.tag.service.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InstrumentTagCommand {

    private String instrumentTagName;
    private String changeInstrumentTagName;

}
