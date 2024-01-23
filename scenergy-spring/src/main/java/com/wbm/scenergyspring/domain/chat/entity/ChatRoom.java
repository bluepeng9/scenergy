package com.wbm.scenergyspring.domain.chat.entity;

import com.wbm.scenergyspring.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ChatRoom extends BaseEntity {

    @Id
    @Column(name = "room_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_name")
    private String name;

    @Column(name = "status")
    private int status;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    private List<ChatUser> chatUsers;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    private List<ChatMessage> chatMessages;

    public static ChatRoom createNewRoom(
            String name,
            int status,
            List<ChatUser> chatUsers
    ) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.name = name;
        chatRoom.status = status;
        chatRoom.chatUsers.addAll(chatUsers);
        return chatRoom;
    }
}
