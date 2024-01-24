package com.wbm.scenergyspring.domain.portfolio.service;

import com.wbm.scenergyspring.domain.portfolio.entity.Portfolio;
import com.wbm.scenergyspring.domain.portfolio.repository.PortfolioRepository;
import com.wbm.scenergyspring.domain.portfolio.service.command.CreatePortfolioCommand;
import com.wbm.scenergyspring.domain.portfolio.service.command.DeletePortfolioCommand;
import com.wbm.scenergyspring.domain.portfolio.service.command.GetPortfolioCommand;
import com.wbm.scenergyspring.domain.portfolio.service.command.UpdatePortfolioCommand;
import com.wbm.scenergyspring.global.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PortfolioService {

    final PortfolioRepository portfolioRepository;

    @Transactional(readOnly = false)
    public Long createPortfolio(CreatePortfolioCommand command) {
        boolean alreadyCreated = portfolioRepository.existsById(command.getUserId());
        if (alreadyCreated) {
            throw new IllegalStateException("이미 존재하는 포트폴리오");
        }
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
    @Transactional(readOnly = false)
    public Long deletePortfolio(DeletePortfolioCommand command) {

        Portfolio existPortfolio = portfolioRepository.findByIdJoin(command.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("포트폴리오가 존재하지 않는 회원"));
        if (!existPortfolio.getId().equals(command.getPortfolioId())) {
            throw new IllegalStateException("삭제권한이 없는 회원");
        }

        portfolioRepository.deleteById(command.getPortfolioId());
        return command.getPortfolioId();
    }

    @Transactional
    public Portfolio getPortfolio(GetPortfolioCommand command) {
        return portfolioRepository.findByIdJoin(command.getPortfolioId())
                .orElseThrow(() -> new EntityNotFoundException("DB에 저장되지 않은 포트폴리오"));
    }
}