package com.wbm.scenergyspring.domain.post.jobPost.service.Command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CancleApplicationCommand {
	Long jobPostId;
	Long userId;
}
