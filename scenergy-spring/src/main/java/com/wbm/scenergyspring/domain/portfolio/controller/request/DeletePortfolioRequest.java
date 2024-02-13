package com.wbm.scenergyspring.domain.portfolio.controller.request;

import com.wbm.scenergyspring.domain.portfolio.service.command.DeletePortfolioCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeletePortfolioRequest {
    Long portfolioId;
    Long userId;

    public DeletePortfolioCommand toDeletePortfolioCommand() {
        DeletePortfolioCommand command = DeletePortfolioCommand.builder()
                .portfolioId(portfolioId)
                .userId(userId)
                .build();
        return command;
    }
}
