package com.wbm.scenergyspring.domain.portfolio.controller.request;

import com.wbm.scenergyspring.domain.portfolio.service.command.GetPortfolioCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetPortfolioRequest {
    Long portfolioId;

    public GetPortfolioCommand toGetPortfolioCommand() {
        GetPortfolioCommand command = GetPortfolioCommand.builder()
                .portfolioId(portfolioId)
                .build();
        return command;
    }
}
