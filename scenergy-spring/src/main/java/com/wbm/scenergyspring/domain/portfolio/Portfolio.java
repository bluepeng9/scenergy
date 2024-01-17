package com.wbm.scenergyspring.domain.portfolio;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_id")
    private Long id;
    private int userId;
    private String description;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "portfolio")
    private List<Education> educations = new ArrayList<>();
    @OneToMany(mappedBy = "portfolio")
    private List<Experience> experiences = new ArrayList<>();
    @OneToMany(mappedBy = "portfolio")
    private List<Honor> honors = new ArrayList<>();
    @OneToMany(mappedBy = "portfolio")
    private List<PortfolioEtc> etcs = new ArrayList<>();

    public static Portfolio createNewPortfolio(
            int userId,
            String description,
            List<Education> educations,
            List<Experience> experiences,
            List<Honor> honors,
            List<PortfolioEtc> etcs
    ){
        Portfolio portfolio = new Portfolio();
        portfolio.userId = userId;
        portfolio.description = description;
        portfolio.educations = educations;
        portfolio.experiences = experiences;
        portfolio.honors = honors;
        portfolio.etcs = etcs;
        return portfolio;
    }
}
