package com.wbm.scenergyspring.util;

import com.wbm.scenergyspring.domain.chat.dto.ChatRoomDto;
import com.wbm.scenergyspring.domain.chat.entity.ChatRoom;
import com.wbm.scenergyspring.domain.chat.service.ChatService;
import com.wbm.scenergyspring.domain.chat.service.command.CreateChatRoomCommand;
import com.wbm.scenergyspring.domain.follow.service.FollowService;
import com.wbm.scenergyspring.domain.follow.service.command.FollowUserCommand;
import com.wbm.scenergyspring.domain.user.entity.Gender;
import com.wbm.scenergyspring.domain.user.entity.User;
import com.wbm.scenergyspring.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DummyGenerator {
    final UserRepository userRepository;
    final ChatService chatService;
    final FollowService followService;
    private static int userCount = 1;
    private static int chatRoomCount = 1;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void init() {
        createAdminUser();
        List<User> dummyUsers = createDummyUsers(3);
        createDummyChatRoom(dummyUsers.get(0), dummyUsers.get(1));
        createDummyChatRoom(dummyUsers.get(0), dummyUsers.get(2));
        createFollowing(dummyUsers.get(0), dummyUsers.get(1));
        createFollowing(dummyUsers.get(0), dummyUsers.get(2));
        createFollowing(dummyUsers.get(1), dummyUsers.get(0));
        createFollowing(dummyUsers.get(1), dummyUsers.get(2));
        createFollowing(dummyUsers.get(2), dummyUsers.get(0));
        createFollowing(dummyUsers.get(2), dummyUsers.get(1));
        log.info("DummyGenerating success");
    }

    private void createAdminUser() {
        User user = User.createNewUser(
                "admin@scenergy.com",
                "1234",
                "admin",
                Gender.MALE,
                "admin"
        );
        userRepository.save(user);
    }

    public List<User> createDummyUsers(int count) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            User user = User.createNewUser(
                    "email" + userCount + "@test.com",
                    "password" + userCount,
                    "username" + userCount,
                    Gender.MALE,
                    "uniquenickname" + userCount
            );
            users.add(user);
            userCount += 1;
            userRepository.save(user);
        }
        return users;
    }

    public ChatRoom createDummyChatRoom(User... users) {
        List<User> userList = new ArrayList<>();
        for (User user : users) {
            userList.add(user);
        }
        CreateChatRoomCommand command = CreateChatRoomCommand.builder()
                .users(userList)
                .roomName("room" + chatRoomCount)
                .status(userList.size() < 3 ? 0 : 1)
                .build();
        ChatRoomDto chatRoom = chatService.createChatRoom(command);
        chatRoomCount += 1;
        return chatService.findChatRoom(chatRoom.getId());
    }

    public void createFollowing(User toUser, User fromUser) {
        FollowUserCommand followUserCommand = FollowUserCommand.builder()
                .toUserId(toUser.getId())
                .fromUserId(fromUser.getId())
                .build();
        followService.followUser(followUserCommand);
    }
}
