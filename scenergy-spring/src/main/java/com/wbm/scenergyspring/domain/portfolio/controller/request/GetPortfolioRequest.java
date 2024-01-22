package com.wbm.scenergyspring.domain.portfolio.controller.request;

import com.wbm.scenergyspring.domain.portfolio.service.command.GetPortfolioCommand;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetPortfolioRequest {
    Long portfolioId;

    public GetPortfolioCommand toGetPortfolioCommand() {
        GetPortfolioCommand command = GetPortfolioCommand.builder()
                .portfolioId(portfolioId)
                .build();
        return command;
    }
}
