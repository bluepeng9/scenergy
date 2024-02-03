package com.wbm.scenergyspring.domain.chat.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.wbm.scenergyspring.domain.user.entity.User;
import com.wbm.scenergyspring.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ChatOnlineInfo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private ChatRoom chatRoom;

    @JsonBackReference(value = "user-chat_online_infos")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Boolean onlineStatus;

    public static ChatOnlineInfo createOnlineInfo(ChatRoom chatRoom, User user, boolean onlineStatus) {
        ChatOnlineInfo onlineInfo = new ChatOnlineInfo();
        onlineInfo.chatRoom = chatRoom;
        onlineInfo.user = user;
        onlineInfo.onlineStatus = onlineStatus;
        return onlineInfo;
    }

}