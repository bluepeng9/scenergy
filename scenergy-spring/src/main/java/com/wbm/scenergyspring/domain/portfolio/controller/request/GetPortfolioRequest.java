package com.wbm.scenergyspring.domain.portfolio.controller.request;

import com.wbm.scenergyspring.domain.portfolio.service.command.GetPortfolioCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetPortfolioRequest {
    Long userId;

    public GetPortfolioCommand toGetPortfolioCommand() {
        GetPortfolioCommand command = GetPortfolioCommand.builder()
                .userId(userId)
                .build();
        return command;
    }
}
