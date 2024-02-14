package com.wbm.scenergyspring.domain.portfolio.entity;

import com.wbm.scenergyspring.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Experience extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exp_id")
    private Long id;
    private String company;
    private String position;
    private LocalDateTime expStartDate;
    private LocalDateTime expEndDate;

    public static Experience createNewExperience(
            String company,
            String position,
            LocalDateTime expStartDate,
            LocalDateTime expEndDate
    ){
        Experience experience = new Experience();
        experience.company = company;
        experience.position = position;
        experience.expStartDate = expStartDate;
        experience.expEndDate = expEndDate;
        return experience;
    }
}
