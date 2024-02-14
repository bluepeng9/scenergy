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
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Education> educations = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Experience> experiences = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Honor> honors = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
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
            this.educations.addAll(educations);
        }

        this.experiences.clear();
        if (experiences != null) {
            this.experiences.addAll(experiences);
        }

        this.honors.clear();
        if (honors != null) {
            this.honors.addAll(honors);
        }

        this.etcs.clear();
        if (etcs != null) {
            this.etcs.addAll(etcs);
        }
    }
}
