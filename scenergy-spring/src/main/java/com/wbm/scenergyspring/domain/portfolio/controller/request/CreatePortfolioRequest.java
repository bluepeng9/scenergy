package com.wbm.scenergyspring.domain.portfolio.controller.request;

import com.wbm.scenergyspring.domain.portfolio.service.command.CreatePortfolioCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePortfolioRequest {
    Long userId;
    public CreatePortfolioCommand toCreatePortfolioCommand() {
        CreatePortfolioCommand command = CreatePortfolioCommand.builder()
                .userId(userId)
                .build();
        return command;
    }
}
