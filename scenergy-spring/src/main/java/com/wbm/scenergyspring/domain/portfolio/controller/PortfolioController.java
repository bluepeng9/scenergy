package com.wbm.scenergyspring.domain.portfolio.controller;

import com.wbm.scenergyspring.domain.portfolio.controller.request.CreatePortfolioRequest;
import com.wbm.scenergyspring.domain.portfolio.controller.request.UpdatePortfolioRequest;
import com.wbm.scenergyspring.domain.portfolio.controller.response.CreatePortfolioResponse;
import com.wbm.scenergyspring.domain.portfolio.controller.response.UpdatePortfolioResponse;
import com.wbm.scenergyspring.domain.portfolio.service.PortfolioService;
import com.wbm.scenergyspring.domain.user.controller.response.CreateUserResponse;
import com.wbm.scenergyspring.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiResponse<CreatePortfolioResponse>> createPortfolio(
            @RequestBody CreatePortfolioRequest request
    ) {
        Long portfolioId = portfolioService.createPortfolio(request.toCreatePortfolioCommand());

        CreatePortfolioResponse createPortfolioResponse = new CreatePortfolioResponse();
        createPortfolioResponse.setPortfolioId(portfolioId);

        return ResponseEntity.ok(ApiResponse.createSuccess(createPortfolioResponse));
    }
    @PostMapping("/update")
    public ResponseEntity<ApiResponse<UpdatePortfolioResponse>> updatePortfolio(
            @RequestBody UpdatePortfolioRequest request
    ) {
        Long portfolioId = portfolioService.updatePortfolio(request.toUpdatePortfolioCommand());
        UpdatePortfolioResponse updatePortfolioResponse = new UpdatePortfolioResponse();
        updatePortfolioResponse.setPortfolioId(portfolioId);
        return ResponseEntity.ok(ApiResponse.createSuccess(updatePortfolioResponse));
    }
}