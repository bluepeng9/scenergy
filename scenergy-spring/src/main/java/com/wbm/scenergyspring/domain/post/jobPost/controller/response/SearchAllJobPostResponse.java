package com.wbm.scenergyspring.domain.post.jobPost.controller.response;

import com.wbm.scenergyspring.domain.post.jobPost.entity.IsActive;
import com.wbm.scenergyspring.domain.post.jobPost.service.Command.JobPostGenreTagCommand;
import com.wbm.scenergyspring.domain.post.jobPost.service.Command.JobPostInstrumentCommand;
import com.wbm.scenergyspring.domain.post.jobPost.service.Command.JobPostLocationCommand;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class SearchAllJobPostResponse {

    Long jobPostId;
    Long userId;
    String title;
    String content;
    String nickname;
    LocalDateTime expirationDate;
    Long peopleRecruited;
    Long bookMark;
    Long totalApplicant;
    IsActive isActive;
    List<JobPostGenreTagCommand> genreTags;
    List<JobPostInstrumentCommand> instrumentTags;
    List<JobPostLocationCommand> locationTags;

}
