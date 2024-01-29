package com.wbm.scenergyspring.domain.chat.service;

import com.wbm.scenergyspring.domain.chat.entity.ChatRoom;
import com.wbm.scenergyspring.domain.chat.repository.ChatRoomRepository;
import com.wbm.scenergyspring.domain.chat.repository.ChatUserRepository;
import com.wbm.scenergyspring.domain.chat.service.command.CreateChatRoomCommand;
import com.wbm.scenergyspring.domain.chat.service.command.InviteChatRoomCommand;
import com.wbm.scenergyspring.domain.chat.service.command.RenameChatRoomCommand;
import com.wbm.scenergyspring.domain.user.entity.User;
import com.wbm.scenergyspring.domain.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
class ChatServiceTest {

    @Autowired
    ChatService chatService;
    @Autowired
    ChatUserRepository chatUserRepository;
    @Autowired
    ChatRoomRepository chatRoomRepository;
    @Autowired
    UserRepository userRepository;

    CreateChatRoomCommand createChatRoomCommand() {
        User user1 = User.createNewUser("email1", "1", "name1");
        User user2 = User.createNewUser("email2", "2", "name2");
        userRepository.save(user1);
        userRepository.save(user2);
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        CreateChatRoomCommand command = CreateChatRoomCommand
                .builder()
                .roomName("room1")
                .status(0)
                .users(users)
                .build();
        return command;
    }

    @Test
    @Transactional
    void 채팅룸_생성_테스트() {
        //given
        CreateChatRoomCommand command = createChatRoomCommand();
        //when
        Long chatRoomId = chatService.createChatRoom(command);
        //then
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).get();
        Assertions.assertThat(chatRoom.getChatUsers().get(0).getChatRoom()).isEqualTo(chatRoom.getChatUsers().get(1).getChatRoom());
        Assertions.assertThat(chatRoom.getChatUsers().get(0).getUser()).isEqualTo(command.getUsers().get(0));
    }

    @Test
    @Transactional
    void 채팅룸_이름변경_테스트() {
        //given
        CreateChatRoomCommand createCommand = createChatRoomCommand();
        Long createChatRoomId = chatService.createChatRoom(createCommand);
        //when
        RenameChatRoomCommand renameCommand = RenameChatRoomCommand.builder()
                .roomId(createChatRoomId)
                .roomName("newName")
                .build();
        Long renameChatRoomId = chatService.renameChatRoom(renameCommand);
        //then
        ChatRoom chatRoom = chatRoomRepository.findById(renameChatRoomId).get();
        Assertions.assertThat(chatRoom.getName()).isEqualTo(renameCommand.getRoomName());
        Assertions.assertThat(renameChatRoomId).isEqualTo(createChatRoomId);
    }

    @Test
    @Transactional
    void 채팅룸_초대_테스트() {
        //given
        CreateChatRoomCommand createCommand = createChatRoomCommand();
        Long createChatRoomId = chatService.createChatRoom(createCommand);
        ChatRoom createChatRoom = chatRoomRepository.findById(createChatRoomId).get();
        int beforeSize = createChatRoom.getChatUsers().size();

        User newUser = User.createNewUser("email3", "3", "name3");
        List<User> users = new ArrayList<>();
        users.add(newUser);
        InviteChatRoomCommand inviteChatRoomCommand = InviteChatRoomCommand.builder()
                .users(users)
                .roomId(createChatRoomId)
                .build();
        //when
        Long inviteChatRoomId = chatService.inviteChatRoom(inviteChatRoomCommand);
        //then
        ChatRoom inviteChatRoom = chatRoomRepository.findById(inviteChatRoomId).get();
        Assertions.assertThat(inviteChatRoom).isEqualTo(createChatRoom);
        Assertions.assertThat(inviteChatRoom.getChatUsers().size()).isEqualTo(beforeSize + 1);
        Assertions.assertThat(inviteChatRoom.getChatUsers().get(inviteChatRoom.getChatUsers().size() - 1).getUser()).isEqualTo(newUser);
    }

    @Test
    @Transactional
    void 마이_채팅룸_조회_테스트() {
        //given
        User user1 = User.createNewUser("email1", "1", "name1");
        User user2 = User.createNewUser("email2", "2", "name2");
        User user3 = User.createNewUser("email3", "3", "name3");
        User save1 = userRepository.save(user1);
        User save2 = userRepository.save(user2);
        User save3 = userRepository.save(user3);

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        CreateChatRoomCommand command = CreateChatRoomCommand
                .builder()
                .roomName("room1")
                .status(0)
                .users(users)
                .build();
        Long chatRoom1 = chatService.createChatRoom(command);


        List<User> users2 = new ArrayList<>();
        users2.add(user1);
        users2.add(user3);
        CreateChatRoomCommand command2 = CreateChatRoomCommand
                .builder()
                .roomName("room2")
                .status(0)
                .users(users2)
                .build();
        Long chatRoom2 = chatService.createChatRoom(command2);

        //when
        List<ChatRoom> myChatRoom = chatRoomRepository.findMyChatRoom(save1.getId());
        List<ChatRoom> myChatRoom2 = chatRoomRepository.findMyChatRoom(save3.getId());
        //then
        Assertions.assertThat(myChatRoom.size()).isEqualTo(2);
        Assertions.assertThat(myChatRoom2.get(0).getChatUsers().get(0).getUser()).isEqualTo(myChatRoom.get(0).getChatUsers().get(0).getUser());
    }
}