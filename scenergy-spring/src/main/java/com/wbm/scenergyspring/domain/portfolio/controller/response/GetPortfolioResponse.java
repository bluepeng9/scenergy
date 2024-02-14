package com.wbm.scenergyspring.domain.portfolio.controller.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.wbm.scenergyspring.domain.portfolio.entity.*;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class GetPortfolioResponse {
    PortfolioDto portfolio;

    @Data
    @Builder
    public static class PortfolioDto implements Serializable {
        private Long id;
        private Long userId;
        private String description;
        private List<EducationDto> educations;
        private List<ExperienceDto> experiences;
        private List<HonorDto> honors;
        private List<PortfolioEtcDto> etcs;

        public static PortfolioDto from(Portfolio portfolio) {
            return PortfolioDto.builder()
                    .id(portfolio.getId())
                    .userId(portfolio.getUserId())
                    .description(portfolio.getDescription())
                    .educations(EducationDto.from(portfolio.getEducations()))
                    .experiences(ExperienceDto.from(portfolio.getExperiences()))
                    .honors(HonorDto.from(portfolio.getHonors()))
                    .etcs(PortfolioEtcDto.from(portfolio.getEtcs()))
                    .build();
        }
    }

    @Data
    @Builder
    static class EducationDto implements Serializable {
        private String institution;
        private String degree;
        private String major;
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        private LocalDateTime graduationDate;
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        private LocalDateTime admissionDate;

        static List<EducationDto> from(List<Education> educations) {
            if (educations == null) return null;
            List<EducationDto> educationDtos = new ArrayList<>();
            for (Education edu : educations) {
                EducationDto educationDto = EducationDto.builder()
                        .institution(edu.getInstitution())
                        .degree(edu.getDegree())
                        .major(edu.getMajor())
                        .admissionDate(edu.getAdmissionDate())
                        .graduationDate(edu.getGraduationDate())
                        .build();
                educationDtos.add(educationDto);
            }
            return educationDtos;
        }
    }

    @Data
    @Builder
    static class ExperienceDto implements Serializable {
        private String company;
        private String position;
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        private LocalDateTime expStartDate;
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        private LocalDateTime expEndDate;

        static List<ExperienceDto> from(List<Experience> exps) {
            if (exps == null) return null;
            List<ExperienceDto> experienceDtos = new ArrayList<>();
            for (Experience exp : exps) {
                ExperienceDto experienceDto = ExperienceDto.builder()
                        .company(exp.getCompany())
                        .position(exp.getPosition())
                        .expStartDate(exp.getExpStartDate())
                        .expEndDate(exp.getExpEndDate())
                        .build();
                experienceDtos.add(experienceDto);
            }
            return experienceDtos;
        }
    }

    @Data
    @Builder
    static class HonorDto implements Serializable {
        private String honorTitle;
        private String organization;
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        private LocalDateTime receicedDate;

        static List<HonorDto> from(List<Honor> honors) {
            if (honors == null) return null;
            List<HonorDto> honorDtos = new ArrayList<>();
            for (Honor honor : honors) {
                HonorDto honorDto = HonorDto.builder()
                        .honorTitle(honor.getHonorTitle())
                        .organization(honor.getOrganization())
                        .receicedDate(honor.getReceicedDate())
                        .build();
                honorDtos.add(honorDto);
            }
            return honorDtos;
        }
    }

    @Data
    @Builder
    static class PortfolioEtcDto implements Serializable {
        private String etcTitle;
        private String etcDescription;
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        private LocalDateTime etcStartDate;
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        private LocalDateTime etcEndDate;

        static List<PortfolioEtcDto> from(List<PortfolioEtc> portfolioEtcs) {
            if (portfolioEtcs == null) return null;
            List<PortfolioEtcDto> portfolioEtcDtos = new ArrayList<>();
            for (PortfolioEtc etc : portfolioEtcs) {
                PortfolioEtcDto etcDto = PortfolioEtcDto.builder()
                        .etcDescription(etc.getEtcDescription())
                        .etcTitle(etc.getEtcTitle())
                        .etcStartDate(etc.getEtcStartDate())
                        .etcEndDate(etc.getEtcEndDate())
                        .build();
                portfolioEtcDtos.add(etcDto);
            }
            return portfolioEtcDtos;
        }
    }
}