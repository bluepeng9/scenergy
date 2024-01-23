package com.wbm.scenergyspring.domain.portfolio.service;

import com.wbm.scenergyspring.domain.portfolio.entity.*;
import com.wbm.scenergyspring.domain.portfolio.repository.PortfolioRepository;
import com.wbm.scenergyspring.domain.portfolio.service.command.CreatePortfolioCommand;
import com.wbm.scenergyspring.domain.portfolio.service.command.DeletePortfolioCommand;
import com.wbm.scenergyspring.domain.portfolio.service.command.UpdatePortfolioCommand;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(properties = "spring.jpa.properties.hibernate.default_batch_fetch_size=1000")
class PortfolioServiceTest {

    @Autowired
    PortfolioService portfolioService;
    @Autowired
    PortfolioRepository portfolioRepository;

    CreatePortfolioCommand createPortfolioCommand() {
        return CreatePortfolioCommand.builder()
                .userId(1L)
                .build();
    }

    UpdatePortfolioCommand updatePortfolioCommand(Long portfolioId) {
        //하위 entity 생성
        List<Education> educations = new ArrayList<>();
        List<PortfolioEtc> etcs = new ArrayList<>();
        List<Experience> experiences = new ArrayList<>();
        List<Honor> honors = new ArrayList<>();
        educations.add(Education.createNewEducation("inst", "degree", "major", LocalDateTime.now(), LocalDateTime.now()));
        etcs.add(PortfolioEtc.createNewPortfolioEtc("title", "desc"));
        experiences.add(Experience.createNewExperience("company", "position"));
        honors.add(Honor.createNewHonor("title", "orgam"));
        //command 발행
        return UpdatePortfolioCommand.builder()
                .portfolioId(portfolioId)
                .userId(2L)
                .description("port desc")
                .educations(educations)
                .etcs(etcs)
                .experiences(experiences)
                .honors(honors)
                .build();
    }

    DeletePortfolioCommand deletePortfolioCommand(Long portfolioId) {
        return DeletePortfolioCommand.builder()
                .portfolioId(portfolioId)
                .userId(1L)
                .build();
    }

    @Test
    void 포트폴리오_생성_테스트() {
        //given
        CreatePortfolioCommand command = createPortfolioCommand();
        //when
        portfolioService.createPortfolio(command);
        //then
        assertTrue(portfolioRepository.findByIdJoin(1L).isPresent());
    }


    @Test
    void 포트폴리오_수정_테스트() {
        //given
        Long portfolioId = portfolioService.createPortfolio(createPortfolioCommand());
        UpdatePortfolioCommand updateCommand = updatePortfolioCommand(portfolioId);
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
    void 포트폴리오_삭제_테스트() {
        //given
        Long portfolioId = portfolioService.createPortfolio(createPortfolioCommand());
        DeletePortfolioCommand deletePortfolioCommand = deletePortfolioCommand(portfolioId);

        //when
        Long deletedPortfolioId = portfolioService.deletePortfolio(deletePortfolioCommand);

        //then
        assertThrows(EntityNotFoundException.class, () -> {
            portfolioRepository.findById(portfolioId)
                    .orElseThrow(() -> new EntityNotFoundException("DB에 존재하지 않음"));
        });
    }


    @Test
    void 포트폴리오_조회_테스트() {
        //given

        Long portfolioId = portfolioService.createPortfolio(createPortfolioCommand());
        UpdatePortfolioCommand updateCommand = updatePortfolioCommand(portfolioId);
        Portfolio beforePortfolio = portfolioRepository.findByIdJoin(portfolioId).get();
        beforePortfolio.updatePortfolio(
                updateCommand.getDescription(),
                updateCommand.getExperiences(),
                updateCommand.getHonors(),
                updateCommand.getEtcs(),
                updateCommand.getEducations()
        );

        //when
        Portfolio afterPortfolio = portfolioRepository.findByIdJoin(portfolioId).get();

        //then
        assertEquals(updateCommand.getEducations(), afterPortfolio.getEducations());
    }
}