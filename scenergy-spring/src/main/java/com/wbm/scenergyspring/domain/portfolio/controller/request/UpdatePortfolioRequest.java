package com.wbm.scenergyspring.domain.portfolio.controller.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.wbm.scenergyspring.domain.portfolio.entity.Education;
import com.wbm.scenergyspring.domain.portfolio.entity.Experience;
import com.wbm.scenergyspring.domain.portfolio.entity.Honor;
import com.wbm.scenergyspring.domain.portfolio.entity.PortfolioEtc;
import com.wbm.scenergyspring.domain.portfolio.service.command.UpdatePortfolioCommand;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class UpdatePortfolioRequest {
    Long portfolioId;
    Long userId;
    String description;
    List<EducationDto> educations;
    List<ExperienceDto> experiences;
    List<HonorDto> honors;
    List<PortfolioEtcDto> etcs;

    public UpdatePortfolioCommand toUpdatePortfolioCommand() {
        UpdatePortfolioCommand command = UpdatePortfolioCommand.builder()
                .portfolioId(portfolioId)
                .userId(userId)
                .description(description)
                .etcs(PortfolioEtcDto.convert(etcs))
                .honors(HonorDto.convert(honors))
                .experiences(ExperienceDto.convert(experiences))
                .educations(EducationDto.convert(educations))
                .build();
        return command;
    }

    @Data
    @NoArgsConstructor
    static class EducationDto {
        private String institution;
        private String degree;
        private String major;
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        private LocalDateTime graduationDate;
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        private LocalDateTime admissionDate;

        static List<Education> convert(List<EducationDto> educationDtos) {
            if (educationDtos == null) return null;
            List<Education> educations = new ArrayList<>();
            for (EducationDto educationDto : educationDtos) {
                Education newEducation = Education.createNewEducation(
                        educationDto.getInstitution(),
                        educationDto.getDegree(),
                        null,
                        educationDto.getAdmissionDate(),
                        educationDto.getGraduationDate()
                );
                educations.add(newEducation);
            }
            return educations;
        }
    }

    @Data
    @NoArgsConstructor
    static class ExperienceDto {
        private String company;
        private String position;
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        private LocalDateTime expStartDate;
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        private LocalDateTime expEndDate;

        static List<Experience> convert(List<ExperienceDto> experienceDtos) {
            if (experienceDtos == null) return null;
            List<Experience> experiences = new ArrayList<>();
            for (ExperienceDto experienceDto : experienceDtos) {
                Experience newExperience = Experience.createNewExperience(
                        experienceDto.getCompany(),
                        experienceDto.getPosition(),
                        experienceDto.getExpStartDate(),
                        experienceDto.getExpEndDate()
                );
                experiences.add(newExperience);
            }
            return experiences;
        }
    }

    @Data
    @NoArgsConstructor
    static class HonorDto {
        private String honorTitle;
        private String organization;
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        private LocalDateTime receicedDate;

        static List<Honor> convert(List<HonorDto> honorDtos) {
            if (honorDtos == null) return null;
            List<Honor> honors = new ArrayList<>();
            for (HonorDto honorDto : honorDtos) {
                Honor newHonor = Honor.createNewHonor(
                        honorDto.getHonorTitle(),
                        honorDto.getOrganization(),
                        honorDto.getReceicedDate()
                );
                honors.add(newHonor);
            }
            return honors;
        }
    }

    @Data
    @NoArgsConstructor
    static class PortfolioEtcDto {
        private String etcTitle;
        private String etcDescription;
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        private LocalDateTime etcStartDate;
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        private LocalDateTime etcEndDate;

        static List<PortfolioEtc> convert(List<PortfolioEtcDto> etcDtos) {
            if (etcDtos == null) return null;
            List<PortfolioEtc> etcs = new ArrayList<>();
            for (PortfolioEtcDto etcDto : etcDtos) {
                PortfolioEtc newPortfolioEtc = PortfolioEtc.createNewPortfolioEtc(
                        etcDto.getEtcTitle(),
                        etcDto.getEtcDescription(),
                        etcDto.getEtcStartDate(),
                        etcDto.getEtcEndDate()
                );
                etcs.add(newPortfolioEtc);
            }
            return etcs;
        }
    }
}
