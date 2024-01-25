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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private ChatRoom chatRoom;

    public static ChatMessage createChatMessage(
            Long userId,
            String messageText,
            ChatRoom chatRoom
    ) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.userId = userId;
        chatMessage.messageText = messageText;
        chatMessage.chatRoom = chatRoom;
        return chatMessage;
    }
}
