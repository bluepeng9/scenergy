package com.wbm.scenergyspring.domain.follow.controller.response;

import java.util.ArrayList;
import java.util.List;

import com.wbm.scenergyspring.domain.follow.entity.Follow;
import com.wbm.scenergyspring.domain.user.entity.User;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FindAllFollowerResponse {
	List<FollowingDto> findAllResponseList;

	public static List<FollowingDto> fromList(List<Follow> followList) {
		List<FollowingDto> followDtoList = new ArrayList<>();
		for (Follow follow : followList) {
			FollowingDto from = FollowingDto.from(follow);
			followDtoList.add(from);
		}
		return followDtoList;
	}
}
	@Data
	@Builder
	class FollowDto {
		Long id;
		User from;
		User to;

		static FollowingDto from(Follow follow) {
			return FollowingDto.builder()
				.id(follow.getId())
				.from(follow.getFrom())
				.to(follow.getTo())
				.build();
		}
	}

