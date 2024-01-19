package com.wbm.scenergyspring.domain.portfolio.controller;

import com.wbm.scenergyspring.domain.portfolio.controller.request.CreatePortfolioRequest;
import com.wbm.scenergyspring.domain.portfolio.controller.request.DeletePortfolioRequest;
import com.wbm.scenergyspring.domain.portfolio.controller.request.GetPortfolioRequest;
import com.wbm.scenergyspring.domain.portfolio.controller.request.UpdatePortfolioRequest;
import com.wbm.scenergyspring.domain.portfolio.controller.response.CreatePortfolioResponse;
import com.wbm.scenergyspring.domain.portfolio.controller.response.DeletePortfolioResponse;
import com.wbm.scenergyspring.domain.portfolio.controller.response.GetPortfolioResponse;
import com.wbm.scenergyspring.domain.portfolio.controller.response.UpdatePortfolioResponse;
import com.wbm.scenergyspring.domain.portfolio.entity.Portfolio;
import com.wbm.scenergyspring.domain.portfolio.service.PortfolioService;
import com.wbm.scenergyspring.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio")
public class PortfolioController {

    final PortfolioService portfolioService;

    @PostMapping("/create-port")
    public ResponseEntity<ApiResponse<CreatePortfolioResponse>> createPortfolio(
            @RequestBody CreatePortfolioRequest request
    ) {
        Long portfolioId = portfolioService.createPortfolio(request.toCreatePortfolioCommand());

        CreatePortfolioResponse createPortfolioResponse = new CreatePortfolioResponse();
        createPortfolioResponse.setPortfolioId(portfolioId);

        return ResponseEntity.ok(ApiResponse.createSuccess(createPortfolioResponse));
    }
    @PostMapping("/update-all")
    public ResponseEntity<ApiResponse<UpdatePortfolioResponse>> updatePortfolio(
            @RequestBody UpdatePortfolioRequest request
    ) {
        Long portfolioId = portfolioService.updatePortfolio(request.toUpdatePortfolioCommand());
        UpdatePortfolioResponse updatePortfolioResponse = new UpdatePortfolioResponse();
        updatePortfolioResponse.setPortfolioId(portfolioId);
        return ResponseEntity.ok(ApiResponse.createSuccess(updatePortfolioResponse));
    }
    @GetMapping("/delete-all")
    public ResponseEntity<ApiResponse<DeletePortfolioResponse>> deletePortfolio(
            @RequestBody DeletePortfolioRequest request
            ){
        Long portfolioId = portfolioService.deletePortfolio(request.toDeletePortfolioCommand());
        DeletePortfolioResponse deletePortfolioResponse = new DeletePortfolioResponse();
        deletePortfolioResponse.setPortfolioId(portfolioId);
        return ResponseEntity.ok(ApiResponse.createSuccess(deletePortfolioResponse));
    }

    @GetMapping("/read")
    public ResponseEntity<ApiResponse<GetPortfolioResponse>> getPortfolio(
            @RequestBody GetPortfolioRequest request
    ) {
        Portfolio portfolio = portfolioService.getPortfolio(request.toGetPortfolioCommand());
        GetPortfolioResponse getPortfolioResponse = new GetPortfolioResponse();
        getPortfolioResponse.setPortfolio(portfolio);
        return ResponseEntity.ok(ApiResponse.createSuccess(getPortfolioResponse));
    }
}