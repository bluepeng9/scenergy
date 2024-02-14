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

public class Honor extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "honor_id")
    private Long id;
    private String honorTitle;
    private String organization;
    private LocalDateTime receicedDate;


    public static Honor createNewHonor(
            String honorTitle,
            String organization,
            LocalDateTime receicedDate
    ){
        Honor honor = new Honor();
        honor.honorTitle = honorTitle;
        honor.organization = organization;
        honor.receicedDate = receicedDate;
        return honor;
    }
}
