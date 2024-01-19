package com.wbm.scenergyspring.domain.portfolio.service.command;

import com.wbm.scenergyspring.domain.portfolio.entity.Education;
import com.wbm.scenergyspring.domain.portfolio.entity.Experience;
import com.wbm.scenergyspring.domain.portfolio.entity.Honor;
import com.wbm.scenergyspring.domain.portfolio.entity.PortfolioEtc;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DeletePortfolioCommand {
    Long portfolioId;
    int userId;
}