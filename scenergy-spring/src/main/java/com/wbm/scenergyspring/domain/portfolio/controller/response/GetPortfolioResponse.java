package com.wbm.scenergyspring.domain.portfolio.controller.response;

import com.wbm.scenergyspring.domain.portfolio.entity.Portfolio;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetPortfolioResponse {
    Portfolio portfolio;
}
