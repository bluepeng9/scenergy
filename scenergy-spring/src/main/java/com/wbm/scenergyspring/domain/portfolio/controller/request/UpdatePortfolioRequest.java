package com.wbm.scenergyspring.domain.portfolio.controller.request;

import com.wbm.scenergyspring.domain.portfolio.entity.Education;
import com.wbm.scenergyspring.domain.portfolio.entity.Experience;
import com.wbm.scenergyspring.domain.portfolio.entity.Honor;
import com.wbm.scenergyspring.domain.portfolio.entity.PortfolioEtc;
import com.wbm.scenergyspring.domain.portfolio.service.command.UpdatePortfolioCommand;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UpdatePortfolioRequest {
    Long portfolioId;
    int userId;
    String description;
    List<Education> educations;
    List<PortfolioEtc> etcs;
    List<Experience> experiences;
    List<Honor> honors;

    public UpdatePortfolioCommand toUpdatePortfolioCommand() {
        UpdatePortfolioCommand command = UpdatePortfolioCommand.builder()
                .portfolioId(portfolioId)
                .userId(userId)
                .description(description)
                .etcs(etcs)
                .honors(honors)
                .experiences(experiences)
                .educations(educations)
                .build();
        return command;
    }
}
