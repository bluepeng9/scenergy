package com.wbm.scenergyspring.domain.portfolio.service;

import com.wbm.scenergyspring.domain.portfolio.Portfolio;
import com.wbm.scenergyspring.domain.portfolio.repository.PortfolioRepository;
import com.wbm.scenergyspring.domain.portfolio.service.command.CreatePortfolioCommand;
import com.wbm.scenergyspring.domain.portfolio.service.command.UpdatePortfolioCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Portfolio portfolio = portfolioRepository.findById(command.getPortfolioId()).get();
        portfolio.updatePortfolio(portfolio, command);
        return command.getPortfolioId();
    }
}