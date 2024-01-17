package com.wbm.scenergyspring.domain.portfolio.service;

import com.wbm.scenergyspring.domain.portfolio.Portfolio;
import com.wbm.scenergyspring.domain.portfolio.repository.PortfolioRepository;
import com.wbm.scenergyspring.domain.portfolio.service.command.CreatePortfolioCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PortfolioService {

    final PortfolioRepository portfolioRepository;

    @Transactional(readOnly = false)
    public void createPortfolio(CreatePortfolioCommand command) {
        Portfolio newPortfolio = Portfolio.createNewPortfolio(
                command.getUserId(),
                command.getDescription(),
                command.getEducations(),
                command.getExperiences(),
                command.getHonors(),
                command.getEtcs()
        );
        portfolioRepository.save(newPortfolio);
    }
}