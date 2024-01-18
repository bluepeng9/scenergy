package com.wbm.scenergyspring.domain.portfolio.service.command;

import com.wbm.scenergyspring.domain.portfolio.Education;
import com.wbm.scenergyspring.domain.portfolio.Experience;
import com.wbm.scenergyspring.domain.portfolio.Honor;
import com.wbm.scenergyspring.domain.portfolio.PortfolioEtc;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UpdatePortfolioCommand {
    Long portfolioId;
    int userId;
    String description;
    List<Education> educations;
    List<PortfolioEtc> etcs;
    List<Experience> experiences;
    List<Honor> honors;
}