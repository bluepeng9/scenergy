package com.wbm.scenergyspring.domain.follow.controller.response;

import java.util.ArrayList;
import java.util.List;

import com.wbm.scenergyspring.domain.follow.entity.Follow;
import com.wbm.scenergyspring.domain.user.entity.User;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FindAllResponse {
	List<FollowDto> findAllResponseList;

	public static List<FollowDto> fromList(List<Follow> followList) {
		List<FollowDto> followDtoList = new ArrayList<>();
		for (Follow follow : followList) {
			FollowDto from = FollowDto.from(follow);
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

		static FollowDto from(Follow follow) {
			return FollowDto.builder()
				.id(follow.getId())
				.from(follow.getFrom())
				.to(follow.getTo())
				.build();
		}
	}

