package com.wbm.scenergyspring.domain.portfolio.service.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetPortfolioCommand {
    Long portfolioId;
}