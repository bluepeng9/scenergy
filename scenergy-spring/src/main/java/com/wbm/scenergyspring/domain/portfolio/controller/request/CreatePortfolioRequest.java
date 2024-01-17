package com.wbm.scenergyspring.domain.portfolio.controller.request;

import com.wbm.scenergyspring.domain.portfolio.Education;
import com.wbm.scenergyspring.domain.portfolio.Experience;
import com.wbm.scenergyspring.domain.portfolio.Honor;
import com.wbm.scenergyspring.domain.portfolio.PortfolioEtc;
import com.wbm.scenergyspring.domain.portfolio.service.command.CreatePortfolioCommand;
import com.wbm.scenergyspring.domain.user.service.command.CreateUserCommand;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CreatePortfolioRequest {
    int userId;
    String description;
    List<Education> educations;
    List<PortfolioEtc> etcs;
    List<Experience> experiences;
    List<Honor> honors;

    public CreatePortfolioCommand toCreatePortfolioCommand() {
        CreatePortfolioCommand command = CreatePortfolioCommand.builder()
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
