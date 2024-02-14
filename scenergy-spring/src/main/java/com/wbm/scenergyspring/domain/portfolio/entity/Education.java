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
public class Education extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "edu_id")
    private Long id;
    private String institution;
    private String degree;
    private String major;
    private LocalDateTime graduationDate;
    private LocalDateTime admissionDate;
    public static Education createNewEducation(
            String institution,
            String degree,
            String major,
            LocalDateTime admissionDate,
            LocalDateTime graduationDate
    ){
        Education education = new Education();
        education.degree = degree;
        education.major = major;
        education.institution = institution;
        education.admissionDate = admissionDate;
        education.graduationDate = graduationDate;
        return education;
    }
}
