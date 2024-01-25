package com.wbm.scenergyspring.domain.chat.entity;

import com.wbm.scenergyspring.domain.user.entity.User;
import com.wbm.scenergyspring.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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
            int status
    ) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.name = name;
        chatRoom.status = status;
        chatRoom.chatUsers = new ArrayList<>();
        chatRoom.chatMessages = new ArrayList<>();
        ChatMessage firstMessage = ChatMessage.createChatMessage(0L, "채팅방이 시작되었습니다.", chatRoom);
        chatRoom.chatMessages.add(firstMessage);
        return chatRoom;
    }

    public void addChatMessages(ChatMessage chatMessage) {
        chatMessages.add(chatMessage);
    }

    public void inviteChatUsers(List<User> users) {
        for (User user : users) {
            ChatUser chatUser = ChatUser.createChatUser(this, user);
            chatUsers.add(chatUser);
        }
    }

    public void rename(String roomName) {
        this.name = roomName;
    }
}
