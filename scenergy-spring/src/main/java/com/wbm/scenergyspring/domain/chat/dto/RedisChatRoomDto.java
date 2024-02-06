package com.wbm.scenergyspring.domain.chat.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RedisChatRoomDto implements Serializable {
    private static final long serialVersionUID = 32112411231245123L;
    private Long roomId;
    private String name;
    private String status;
    private int memberCount;

    public static RedisChatRoomDto createRedisChatRoom(Long roomId, String name, int status, int memberCount) {
        RedisChatRoomDto redisChatRoomDto = new RedisChatRoomDto();
        redisChatRoomDto.roomId = roomId;
        redisChatRoomDto.name = name;
        redisChatRoomDto.status = Integer.toString(status);
        redisChatRoomDto.memberCount = memberCount;
        return redisChatRoomDto;
    }

    public void updateRoomName(String name) {
        this.name = name;
    }

    public void updateMemberCount(int count) {
        this.memberCount += count;
    }
}
