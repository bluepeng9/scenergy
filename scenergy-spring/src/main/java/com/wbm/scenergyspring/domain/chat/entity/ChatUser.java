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
public class ChatUser extends BaseEntity {

    @Id
    @Column(name = "chat_user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference(value = "chatroom-users")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private ChatRoom chatRoom;

    @JsonBackReference(value = "chatuser-user")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public static ChatUser createChatUser(ChatRoom chatRoom, User user) {
        ChatUser chatUser = new ChatUser();
        chatUser.chatRoom = chatRoom;
        chatUser.user = user;
        return chatUser;
    }

    public int joinRoom() {
        chatRoom.getChatUsers().add(this);
        return chatRoom.getChatMessages().size();
    }

    public int leaveRoom() {
        chatRoom.getChatUsers().remove(this);
        return chatRoom.getChatMessages().size();
    }
}