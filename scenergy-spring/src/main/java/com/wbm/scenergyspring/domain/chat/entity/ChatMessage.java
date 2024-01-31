package com.wbm.scenergyspring.domain.chat.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.wbm.scenergyspring.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatMessage extends BaseEntity {

    @Id
    @Column(name = "chat_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long senderId;
    private String messageText;
    private int flag;

    @JsonBackReference(value = "chatroom-messages")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", insertable = false, updatable = false)
    private ChatRoom chatRoom;

    public static ChatMessage createChatMessage(
            Long userId,
            String messageText,
            ChatRoom chatRoom
    ) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.senderId = userId;
        chatMessage.messageText = messageText;
        chatMessage.chatRoom = chatRoom;
        return chatMessage;
    }
}