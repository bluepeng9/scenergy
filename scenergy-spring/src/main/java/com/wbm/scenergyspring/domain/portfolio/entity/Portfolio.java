package com.wbm.scenergyspring.domain.portfolio.entity;

import com.wbm.scenergyspring.domain.portfolio.service.command.UpdatePortfolioCommand;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@ToString
@Getter
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
    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL)
    private List<Education> educations = new ArrayList<>();
    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL)
    private List<Experience> experiences = new ArrayList<>();
    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL)
    private List<Honor> honors = new ArrayList<>();
    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL)
    private List<PortfolioEtc> etcs = new ArrayList<>();

    /**
     * create시 호출되는 생성자
     */
    public static Portfolio createNewPortfolio(
            int userId
    ){
        Portfolio portfolio = new Portfolio();
        portfolio.userId = userId;
        return portfolio;
    }

    public Portfolio updatePortfolio(Portfolio portfolio, UpdatePortfolioCommand command){
        portfolio.description = command.getDescription();

        portfolio.educations.clear();
        for (Education edu: command.getEducations()){
            portfolio.educations.add(edu);
            edu.setPortfolio(portfolio);
        }
        portfolio.experiences.clear();
        for (Experience exp:command.getExperiences()){
            portfolio.experiences.add(exp);
            exp.setPortfolio(portfolio);
        }
        portfolio.honors.clear();
        for (Honor honor: command.getHonors()){
            portfolio.honors.add(honor);
            honor.setPortfolio(portfolio);
        }
        portfolio.etcs.clear();
        for (PortfolioEtc etc: command.getEtcs()){
            portfolio.etcs.add(etc);
            etc.setPortfolio(portfolio);
        }
        return portfolio;
    }
}
