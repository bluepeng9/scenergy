package com.wbm.scenergyspring.domain.portfolio.entity;

import com.wbm.scenergyspring.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PortfolioEtc extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "etc_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;
    private String etcTitle;
    private String etcDescription;
    private LocalDateTime etcStartDate;
    private LocalDateTime etcEndDate;
    public static PortfolioEtc createNewPortfolioEtc(
            String etcTitle,
            String etcDescription
    ){
        PortfolioEtc etc = new PortfolioEtc();
        etc.etcTitle = etcTitle;
        etc.etcDescription = etcDescription;
        return etc;
    }
    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }
}
