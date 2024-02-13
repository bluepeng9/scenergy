package com.wbm.scenergyspring.domain.portfolio.controller.request;

import com.wbm.scenergyspring.domain.portfolio.entity.Education;
import com.wbm.scenergyspring.domain.portfolio.entity.Experience;
import com.wbm.scenergyspring.domain.portfolio.entity.Honor;
import com.wbm.scenergyspring.domain.portfolio.entity.PortfolioEtc;
import com.wbm.scenergyspring.domain.portfolio.service.command.UpdatePortfolioCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePortfolioRequest {
    Long portfolioId;
    Long userId;
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
