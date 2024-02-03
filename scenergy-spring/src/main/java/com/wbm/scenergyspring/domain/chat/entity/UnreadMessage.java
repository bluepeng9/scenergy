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
public class UnreadMessage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private ChatRoom chatRoom;

    @JsonBackReference(value = "user-unread_messages")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id")
    private ChatMessage chatMessage;

    public static UnreadMessage createUnreadMessage(ChatRoom chatRoom, User user, ChatMessage chatMessage) {
        UnreadMessage unreadMessage = new UnreadMessage();
        unreadMessage.chatRoom = chatRoom;
        unreadMessage.user = user;
        unreadMessage.chatMessage = chatMessage;
        return unreadMessage;
    }
}