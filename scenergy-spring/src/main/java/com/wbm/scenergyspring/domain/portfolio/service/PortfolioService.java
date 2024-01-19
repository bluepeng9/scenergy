package com.wbm.scenergyspring.domain.portfolio.service;

import com.wbm.scenergyspring.domain.portfolio.entity.Portfolio;
import com.wbm.scenergyspring.domain.portfolio.repository.PortfolioRepository;
import com.wbm.scenergyspring.domain.portfolio.service.command.CreatePortfolioCommand;
import com.wbm.scenergyspring.domain.portfolio.service.command.UpdatePortfolioCommand;
import com.wbm.scenergyspring.global.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PortfolioService {

    final PortfolioRepository portfolioRepository;

    @Transactional(readOnly = false)
    public Long createPortfolio(CreatePortfolioCommand command) {
        Portfolio newPortfolio = Portfolio.createNewPortfolio(command.getUserId());
        return portfolioRepository.save(newPortfolio).getId();
    }
    @Transactional(readOnly = false)
    public Long updatePortfolio(UpdatePortfolioCommand command) {
        Portfolio portfolio = portfolioRepository.findById(command.getPortfolioId())
                .orElseThrow(()-> new EntityNotFoundException("DB에 저장되지 않은 포트폴리오"));
        portfolio.updatePortfolio(
                command.getDescription(),
                command.getExperiences(),
                command.getHonors(),
                command.getEtcs(),
                command.getEducations()
        );
        return command.getPortfolioId();
    }
}