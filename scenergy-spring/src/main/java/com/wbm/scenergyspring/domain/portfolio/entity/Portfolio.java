package com.wbm.scenergyspring.domain.portfolio.entity;

import com.wbm.scenergyspring.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
//@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Portfolio extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_id")
    private Long id;
    private Long userId;
    private String description;
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
            Long userId
    ){
        Portfolio portfolio = new Portfolio();
        portfolio.userId = userId;
        return portfolio;
    }

    public void updatePortfolio(
            String description,
            List<Experience> experiences,
            List<Honor> honors,
            List<PortfolioEtc> etcs,
            List<Education> educations
    ){
        this.description = description;

        this.educations.clear();
        if (educations != null) {

        for (Education edu: educations){
            this.educations.add(edu);
            edu.setPortfolio(this);
        }
        }

        this.experiences.clear();
        if (experiences != null) {

            for (Experience exp : experiences) {
                this.experiences.add(exp);
                exp.setPortfolio(this);
            }
        }

        this.honors.clear();
        if (honors != null) {
        for (Honor honor: honors){
            this.honors.add(honor);
            honor.setPortfolio(this);
        }
        }

        this.etcs.clear();
        if (etcs != null) {
        for (PortfolioEtc etc: etcs){
            this.etcs.add(etc);
            etc.setPortfolio(this);
        }
        }

    }
}
