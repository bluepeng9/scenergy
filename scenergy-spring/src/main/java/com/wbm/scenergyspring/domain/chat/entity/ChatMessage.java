package com.wbm.scenergyspring.domain.chat.entity;

import com.wbm.scenergyspring.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ChatMessage extends BaseEntity {

    @Id
    @Column(name = "chat_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String messageText;
    private int flag;

    private enum type {
        ENTER, TALK, EXIT
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private ChatRoom chatRoom;


    public static ChatMessage createChatMessage() {
        ChatMessage chatUser = new ChatMessage();
        return chatUser;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }
}
