package com.wbm.scenergyspring.domain.portfolio.entity;

import com.wbm.scenergyspring.domain.portfolio.service.command.UpdatePortfolioCommand;
import com.wbm.scenergyspring.global.entity.BaseEntity;
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
public class Portfolio extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_id")
    private Long id;
    private int userId;
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
            int userId
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
        for (Education edu: educations){
            this.educations.add(edu);
            edu.setPortfolio(this);
        }
        this.experiences.clear();
        for (Experience exp:experiences){
            this.experiences.add(exp);
            exp.setPortfolio(this);
        }
        this.honors.clear();
        for (Honor honor: honors){
            this.honors.add(honor);
            honor.setPortfolio(this);
        }
        this.etcs.clear();
        for (PortfolioEtc etc: etcs){
            this.etcs.add(etc);
            etc.setPortfolio(this);
        }
    }
}
