package com.wbm.scenergyspring.domain.like.controller.response;

import com.wbm.scenergyspring.domain.like.entity.Like;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class GetLikeListResponse {
    Long chatRoomId;
    List<LikeDto> likeList;

    public static List<LikeDto> from(List<Like> likeList) {
        List<LikeDto> likeDtos = new ArrayList<>();
        for (Like like : likeList) {
            likeDtos.add(
                    LikeDto.builder()
                            .id(like.getId())
                            .userId(like.getUser().getId())
                            .videoPostId(like.getVideoPost().getId())
                            .build());
        }
        return likeDtos;
    }
}

@Builder
class LikeDto {
    private Long id;
    private Long videoPostId;
    private Long userId;
}
