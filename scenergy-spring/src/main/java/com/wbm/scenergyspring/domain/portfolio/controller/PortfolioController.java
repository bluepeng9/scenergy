package com.wbm.scenergyspring.domain.portfolio.controller;

import com.wbm.scenergyspring.domain.portfolio.controller.request.CreatePortfolioRequest;
import com.wbm.scenergyspring.domain.portfolio.controller.request.UpdatePortfolioRequest;
import com.wbm.scenergyspring.domain.portfolio.service.PortfolioService;
import com.wbm.scenergyspring.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio")
public class PortfolioController {

    final PortfolioService portfolioService;

    @PostMapping("/create")
    public ApiResponse<Boolean> createPortfolio(
            @RequestBody CreatePortfolioRequest request
    ) {
        portfolioService.createPortfolio(request.toCreatePortfolioCommand());
        return ApiResponse.createSuccess(true);
    }
    @PostMapping("/update")
    public ApiResponse<Boolean> updatePortfolio(
            @RequestBody UpdatePortfolioRequest request
    ) {
        portfolioService.updatePortfolio(request.toUpdatePortfolioCommand());
        return ApiResponse.createSuccess(true);
    }
}