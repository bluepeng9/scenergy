package com.wbm.scenergyspring.domain.portfolio.service;

import com.wbm.scenergyspring.domain.portfolio.*;
import com.wbm.scenergyspring.domain.portfolio.repository.PortfolioRepository;
import com.wbm.scenergyspring.domain.portfolio.service.command.CreatePortfolioCommand;
import com.wbm.scenergyspring.domain.user.service.command.CreateUserCommand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
    void createPortfolio(){
        //given
        List<Education> educations = new ArrayList<>();
        List<PortfolioEtc> etcs = new ArrayList<>();
        List<Experience> experiences = new ArrayList<>();
        List<Honor> honors = new ArrayList<>();

        educations.add( Education.createNewEducation("inst", "degree", "major"));
        etcs.add(PortfolioEtc.createNewPortfolioEtc("title", "desc"));
        experiences.add(Experience.createNewExperience("company","position"));
        honors.add(Honor.createNewHonor("title", "orgam"));

        CreatePortfolioCommand command = CreatePortfolioCommand.builder()
                .userId(1)
                .description("port desc")
                .educations(educations)
                .etcs(etcs)
                .experiences(experiences)
                .honors(honors)
                .build();
        //when
        portfolioService.createPortfolio(command);

        //then
        Portfolio portfolio = portfolioRepository.findById(1L).get();
        System.out.println(portfolio.getDescription());
        String inst = portfolio.getEducations().get(0).getInstitution();
//        System.out.println(portfolio.getEducations().get(0).getInstitution());
//        assertEquals(portfolio.getDescription(), command.getDescription());
    }
}