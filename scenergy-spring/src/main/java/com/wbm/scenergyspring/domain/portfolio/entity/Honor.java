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

public class Honor extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "honor_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;
    private String honorTitle;
    private String organization;
    private LocalDateTime receicedDate;

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    public static Honor createNewHonor(
            String honorTitle,
            String organization
    ){
        Honor honor = new Honor();
        honor.honorTitle = honorTitle;
        honor.organization = organization;
        return honor;
    }
}
