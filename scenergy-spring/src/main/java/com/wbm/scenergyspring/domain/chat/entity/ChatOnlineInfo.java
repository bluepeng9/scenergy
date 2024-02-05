package com.wbm.scenergyspring.domain.chat.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @JsonBackReference(value = "chatuser-chat_online_infos")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_user_id")
    private ChatUser chatUser;

    private Boolean onlineStatus;

    public static ChatOnlineInfo createOnlineInfo(ChatRoom chatRoom, ChatUser chatUser, boolean onlineStatus) {
        ChatOnlineInfo onlineInfo = new ChatOnlineInfo();
        onlineInfo.chatRoom = chatRoom;
        onlineInfo.chatUser = chatUser;
        onlineInfo.onlineStatus = onlineStatus;
        return onlineInfo;
    }

    public void changeStatusOn() {
        onlineStatus = true;
    }

    public void changeStatusOff() {
        onlineStatus = false;
    }


}