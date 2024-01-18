package com.wbm.scenergyspring.domain.portfolio.service;

import com.wbm.scenergyspring.domain.portfolio.*;
import com.wbm.scenergyspring.domain.portfolio.repository.PortfolioRepository;
import com.wbm.scenergyspring.domain.portfolio.service.command.CreatePortfolioCommand;
import com.wbm.scenergyspring.domain.portfolio.service.command.UpdatePortfolioCommand;
import com.wbm.scenergyspring.domain.user.service.command.CreateUserCommand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PortfolioServiceTest {

    @Autowired
    PortfolioService portfolioService;
    @Autowired
    PortfolioRepository portfolioRepository;
    @Test
    @Transactional
    void createPortfolio(){
        //given
        CreatePortfolioCommand command = CreatePortfolioCommand.builder()
                .userId(1)
                .build();
        //when
        portfolioService.createPortfolio(command);
        //then
        assertTrue(portfolioRepository.findById(1L).isPresent());
    }
    @Test
    @Transactional
    void updatePortfolio(){
        //given
        CreatePortfolioCommand createCommand = CreatePortfolioCommand.builder()
                .userId(1)
                .build();
        Long portfolioId = portfolioService.createPortfolio(createCommand);

        List<Education> educations = new ArrayList<>();
        List<PortfolioEtc> etcs = new ArrayList<>();
        List<Experience> experiences = new ArrayList<>();
        List<Honor> honors = new ArrayList<>();

        educations.add( Education.createNewEducation("inst", "degree", "major"));
        etcs.add(PortfolioEtc.createNewPortfolioEtc("title", "desc"));
        experiences.add(Experience.createNewExperience("company","position"));
        honors.add(Honor.createNewHonor("title", "orgam"));

        UpdatePortfolioCommand updateCommand = UpdatePortfolioCommand.builder()
                .portfolioId(portfolioId)
                .userId(2)
                .description("port desc")
                .educations(educations)
                .etcs(etcs)
                .experiences(experiences)
                .honors(honors)
                .build();

        //when
        Portfolio beforePortfolio = portfolioRepository.findById(portfolioId).get();
        beforePortfolio.updatePortfolio(beforePortfolio, updateCommand);

        //then
        Portfolio afterPortfolio = portfolioRepository.findById(portfolioId).get();
        assertEquals(afterPortfolio.getEducations().get(0).getInstitution(), updateCommand.getEducations().get(0).getInstitution());
    }
}