package com.wbm.scenergyspring.domain.portfolio.service;

import com.wbm.scenergyspring.domain.portfolio.entity.*;
import com.wbm.scenergyspring.domain.portfolio.repository.PortfolioRepository;
import com.wbm.scenergyspring.domain.portfolio.service.command.CreatePortfolioCommand;
import com.wbm.scenergyspring.domain.portfolio.service.command.DeletePortfolioCommand;
import com.wbm.scenergyspring.domain.portfolio.service.command.UpdatePortfolioCommand;
import com.wbm.scenergyspring.global.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PortfolioServiceTest {

    @Autowired
    PortfolioService portfolioService;
    @Autowired
    PortfolioRepository portfolioRepository;
    @Test
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
        beforePortfolio.updatePortfolio(
                updateCommand.getDescription(),
                updateCommand.getExperiences(),
                updateCommand.getHonors(),
                updateCommand.getEtcs(),
                updateCommand.getEducations()
        );

        //then
        Portfolio afterPortfolio = portfolioRepository.findById(portfolioId).get();
        assertEquals(afterPortfolio.getEducations().get(0).getInstitution(), updateCommand.getEducations().get(0).getInstitution());
    }
    @Test
    void deletePortfolio(){
        //given
        CreatePortfolioCommand createCommand = CreatePortfolioCommand.builder()
                .userId(1)
                .build();
        Long portfolioId = portfolioService.createPortfolio(createCommand);

        DeletePortfolioCommand deletePortfolioCommand = DeletePortfolioCommand.builder()
                .portfolioId(portfolioId)
                .userId(1)
                .build();

        //when
        Long deletedPortfolioId = portfolioService.deletePortfolio(deletePortfolioCommand);

        //then
        assertThrows(EntityNotFoundException.class, ()->{
            portfolioRepository.findById(portfolioId)
                    .orElseThrow(()-> new EntityNotFoundException("DB에 존재하지 않음"));
        });
    }
}