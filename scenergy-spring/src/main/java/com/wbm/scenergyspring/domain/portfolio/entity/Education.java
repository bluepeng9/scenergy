package com.wbm.scenergyspring.domain.portfolio.entity;

import com.wbm.scenergyspring.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Education extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "edu_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;
    private String institution;
    private String degree;
    private String major;
    private LocalDateTime graduationDate;
    public static Education createNewEducation(
            String institution,
            String degree,
            String major
    ){
        Education education = new Education();
        education.degree = degree;
        education.major = major;
        education.institution = institution;
        return education;
    }
    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }
}
