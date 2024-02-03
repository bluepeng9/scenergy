package com.wbm.scenergyspring.domain.follow.controller.response;

import java.util.List;

import com.wbm.scenergyspring.domain.follow.entity.Follow;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FindAllFollowersResponse {
	List<Follow> followers;
}
