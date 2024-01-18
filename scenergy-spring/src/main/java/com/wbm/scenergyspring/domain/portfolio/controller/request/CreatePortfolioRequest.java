package com.wbm.scenergyspring.domain.portfolio.controller.request;

import com.wbm.scenergyspring.domain.portfolio.service.command.CreatePortfolioCommand;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatePortfolioRequest {
    int userId;
    public CreatePortfolioCommand toCreatePortfolioCommand() {
        CreatePortfolioCommand command = CreatePortfolioCommand.builder()
                .userId(userId)
                .build();
        return command;
    }
}
