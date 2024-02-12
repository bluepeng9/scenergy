package com.wbm.scenergyspring.domain.follow.controller.response;

import java.util.ArrayList;
import java.util.List;

import com.wbm.scenergyspring.domain.follow.entity.Follow;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FindAllFollowingResponse {
	List<FollowingDto> findAllResponseList;

	public static FindAllFollowingResponse fromList(List<Follow> followList) {
		List<FollowingDto> followDtoList = new ArrayList<>();
		for (Follow follow : followList) {
			FollowingDto from = FollowingDto.from(follow);
			followDtoList.add(from);
		}
		return FindAllFollowingResponse.builder()
			.findAllResponseList(followDtoList)
			.build();
	}
}
@Data
@Builder
class FollowingDto {
	Long id;
	String nickname;

	static FollowingDto from(Follow follow) {
		return FollowingDto.builder()
			.id(follow.getId())
			.nickname(follow.getTo().getNickname())
			.build();
	}
}


