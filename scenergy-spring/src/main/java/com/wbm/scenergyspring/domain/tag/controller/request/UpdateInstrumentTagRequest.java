package com.wbm.scenergyspring.domain.tag.controller.request;

import com.wbm.scenergyspring.domain.tag.service.command.InstrumentTagCommand;
import lombok.Data;

@Data
public class UpdateInstrumentTagRequest {

    private String instrumentName;
    private String changeInstrumentName;

    public InstrumentTagCommand toInstrumentTag() {
        InstrumentTagCommand command = InstrumentTagCommand.builder()
                .instrumentTagName(instrumentName)
                .changeInstrumentTagName(changeInstrumentName)
                .build();
        return command;
    }

}
