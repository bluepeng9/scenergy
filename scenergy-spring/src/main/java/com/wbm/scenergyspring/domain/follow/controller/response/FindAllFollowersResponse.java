package com.wbm.scenergyspring.domain.follow.controller.response;

import java.util.List;

import com.wbm.scenergyspring.domain.follow.entity.Follow;

import lombok.Data;

@Data
public class FindAllFollowersResponse {
	List<Follow> followers;
}
