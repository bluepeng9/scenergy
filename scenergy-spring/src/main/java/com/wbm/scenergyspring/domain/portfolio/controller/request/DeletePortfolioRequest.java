package com.wbm.scenergyspring.domain.portfolio.controller.request;

import com.wbm.scenergyspring.domain.portfolio.entity.Education;
import com.wbm.scenergyspring.domain.portfolio.entity.Experience;
import com.wbm.scenergyspring.domain.portfolio.entity.Honor;
import com.wbm.scenergyspring.domain.portfolio.entity.PortfolioEtc;
import com.wbm.scenergyspring.domain.portfolio.service.command.DeletePortfolioCommand;
import com.wbm.scenergyspring.domain.portfolio.service.command.UpdatePortfolioCommand;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
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
