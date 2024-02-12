package com.wbm.scenergyspring.domain.portfolio.service;

import com.wbm.scenergyspring.IntegrationTest;
import com.wbm.scenergyspring.domain.portfolio.entity.*;
import com.wbm.scenergyspring.domain.portfolio.repository.PortfolioRepository;
import com.wbm.scenergyspring.domain.portfolio.service.command.CreatePortfolioCommand;
import com.wbm.scenergyspring.domain.portfolio.service.command.DeletePortfolioCommand;
import com.wbm.scenergyspring.domain.portfolio.service.command.UpdatePortfolioCommand;
import com.wbm.scenergyspring.domain.user.entity.User;
import com.wbm.scenergyspring.domain.user.repository.UserRepository;
import com.wbm.scenergyspring.util.UserGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class PortfolioServiceTest extends IntegrationTest {

    @Autowired
    PortfolioService portfolioService;
    @Autowired
    PortfolioRepository portfolioRepository;
    @Autowired
    UserRepository userRepository;

    CreatePortfolioCommand createPortfolioCommand(User user) {
        return CreatePortfolioCommand.builder()
                .userId(user.getId())
                .build();
    }

    UpdatePortfolioCommand updatePortfolioCommand(Portfolio portfolio) {
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
                .portfolioId(portfolio.getId())
                .userId(portfolio.getUserId())
                .description("portfolio test")
                .educations(educations)
                .etcs(etcs)
                .experiences(experiences)
                .honors(honors)
                .build();
    }

    DeletePortfolioCommand deletePortfolioCommand(Portfolio portfolio) {
        return DeletePortfolioCommand.builder()
                .portfolioId(portfolio.getId())
                .userId(portfolio.getUserId())
                .build();
    }

    @Test
    void 포트폴리오_생성_테스트() {
        //given
        List<User> saveUsers = createSaveUsers(1);
        User user1 = saveUsers.get(0);
        CreatePortfolioCommand command = createPortfolioCommand(user1);
        //when
        Long portfolioId = portfolioService.createPortfolio(command);
        //then
        assertTrue(portfolioRepository.findByIdJoin(portfolioId).isPresent());
    }


    @Test
    void 포트폴리오_수정_테스트() {
        //given
        List<User> saveUsers = createSaveUsers(1);
        User user1 = saveUsers.get(0);
        Long portfolioId = portfolioService.createPortfolio(createPortfolioCommand(user1));
        Portfolio createdPortfolio = portfolioRepository.findByIdJoin(portfolioId).get();
        UpdatePortfolioCommand updateCommand = updatePortfolioCommand(createdPortfolio);
        //when
        createdPortfolio.updatePortfolio(
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
        List<User> saveUsers = createSaveUsers(1);
        User user1 = saveUsers.get(0);
        Long portfolioId = portfolioService.createPortfolio(createPortfolioCommand(user1));
        Portfolio createdPortfolio = portfolioRepository.findByIdJoin(portfolioId).get();
        DeletePortfolioCommand deletePortfolioCommand = deletePortfolioCommand(createdPortfolio);
        //when
        Long deletedPortfolioId = portfolioService.deletePortfolio(deletePortfolioCommand);

        //then
        Assertions.assertThat(portfolioRepository.existsById(deletedPortfolioId)).isFalse();
    }


    @Test
    void 포트폴리오_조회_테스트() {
        //given
        List<User> saveUsers = createSaveUsers(1);
        User user1 = saveUsers.get(0);
        Long portfolioId = portfolioService.createPortfolio(createPortfolioCommand(user1));
        Portfolio createdPortfolio = portfolioRepository.findByIdJoin(portfolioId).get();
        UpdatePortfolioCommand updateCommand = updatePortfolioCommand(createdPortfolio);

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

    List<User> createSaveUsers(int count) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            User user = UserGenerator.createRandomMember();
            users.add(user);
            userRepository.save(user);
        }
        return users;
    }
}