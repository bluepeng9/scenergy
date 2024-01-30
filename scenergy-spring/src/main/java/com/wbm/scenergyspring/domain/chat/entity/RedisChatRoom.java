package com.wbm.scenergyspring.domain.chat.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RedisChatRoom implements Serializable {
    private static final long serialVersionUID = 32112411231245123L;
    private Long roomId;
    private String name;
    private String status;
    private int memberCount;

    public static RedisChatRoom createRedisChatRoom(Long roomId, String name, int status, int memberCount) {
        RedisChatRoom redisChatRoom = new RedisChatRoom();
        redisChatRoom.roomId = roomId;
        redisChatRoom.name = name;
        redisChatRoom.status = Integer.toString(status);
        redisChatRoom.memberCount = memberCount;
        return redisChatRoom;
    }

    public void updateRoomName(String name) {
        this.name = name;
    }

    public void updateMemberCount(int count) {
        this.memberCount += count;
    }
}
