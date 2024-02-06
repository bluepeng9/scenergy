package com.wbm.scenergyspring.domain.chat.service;

import com.wbm.scenergyspring.domain.chat.dto.ChatMessageDto;
import com.wbm.scenergyspring.domain.chat.dto.ChatRoomDto;
import com.wbm.scenergyspring.domain.chat.entity.ChatMessage;
import com.wbm.scenergyspring.domain.chat.entity.ChatRoom;
import com.wbm.scenergyspring.domain.chat.repository.ChatMessageRepository;
import com.wbm.scenergyspring.domain.chat.repository.ChatRoomRepository;
import com.wbm.scenergyspring.domain.chat.repository.ChatUserRepository;
import com.wbm.scenergyspring.domain.chat.service.command.*;
import com.wbm.scenergyspring.domain.user.entity.User;
import com.wbm.scenergyspring.domain.user.repository.UserRepository;
import com.wbm.scenergyspring.util.UserGenerator;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Slf4j
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
    @Autowired
    ChatMessageRepository chatMessageRepository;

    List<User> createSaveUsers(int count) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            User user = UserGenerator.createRandomMember();
            users.add(user);
            userRepository.save(user);
        }
        return users;
    }

    CreateChatRoomCommand createChatRoomCommand(String roomName, List<User> users) {
        CreateChatRoomCommand command = CreateChatRoomCommand
                .builder()
                .roomName(roomName)
                .status(0)
                .users(users)
                .build();
        return command;
    }

    @Test
    @Transactional
    void 채팅룸_생성_테스트() {
        //given
        CreateChatRoomCommand command = createChatRoomCommand("room1", createSaveUsers(2));
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
        CreateChatRoomCommand command = createChatRoomCommand("room1", createSaveUsers(2));
        Long createChatRoomId = chatService.createChatRoom(command);
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
        CreateChatRoomCommand createCommand = createChatRoomCommand("roomtest", createSaveUsers(2));
        Long createChatRoomId = chatService.createChatRoom(createCommand);
        ChatRoom createChatRoom = chatRoomRepository.findById(createChatRoomId).get();
        int beforeSize = createChatRoom.getChatUsers().size();

        List<User> saveUsers = createSaveUsers(1);
        InviteChatRoomCommand inviteChatRoomCommand = InviteChatRoomCommand.builder()
                .users(saveUsers)
                .roomId(createChatRoomId)
                .build();
        //when
        Long inviteChatRoomId = chatService.inviteChatRoom(inviteChatRoomCommand);
        //then
        ChatRoom inviteChatRoom = chatRoomRepository.findById(inviteChatRoomId).get();
        Assertions.assertThat(inviteChatRoom).isEqualTo(createChatRoom);
        Assertions.assertThat(inviteChatRoom.getChatUsers().size()).isEqualTo(beforeSize + 1);
        Assertions.assertThat(inviteChatRoom.getChatUsers().get(inviteChatRoom.getChatUsers().size() - 1).getUser()).isEqualTo(saveUsers.get(0));
    }

    @Test
    @Transactional
    void 마이_채팅룸_조회_테스트() {
        //given
        List<User> saveUsers = createSaveUsers(3);

        List<User> room1users = new ArrayList<>();
        room1users.add(saveUsers.get(0));
        room1users.add(saveUsers.get(1));
        chatService.createChatRoom(createChatRoomCommand("testroom1", room1users));

        List<User> room2users = new ArrayList<>();
        room2users.add(saveUsers.get(0));
        room2users.add(saveUsers.get(2));
        chatService.createChatRoom(createChatRoomCommand("testroom2", room2users));

        //when
        List<ChatRoom> myChatRoom1 = chatRoomRepository.findMyChatRoom(saveUsers.get(0).getId());
        List<ChatRoom> myChatRoom2 = chatRoomRepository.findMyChatRoom(saveUsers.get(1).getId());
        List<ChatRoom> myChatRoom3 = chatRoomRepository.findMyChatRoom(saveUsers.get(2).getId());
        //then
        Assertions.assertThat(myChatRoom1.size()).isEqualTo(room1users.size());
        Assertions.assertThat(myChatRoom1.get(0).getChatUsers().get(0).getUser()).isEqualTo(myChatRoom3.get(0).getChatUsers().get(0).getUser());
        Assertions.assertThat(myChatRoom2.get(0)).isNotEqualTo(myChatRoom3.get(0));
    }

    @Test
    @Transactional
    void 채팅_전송조회_테스트() {
        //given
        List<User> saveUsers = createSaveUsers(2);
        Long chatRoomId = chatService.createChatRoom(createChatRoomCommand("testroom1", saveUsers));
        CreatePubMessageCommand command1 = CreatePubMessageCommand.builder()
                .userId(saveUsers.get(0).getId())
                .roomId(chatRoomId)
                .messageText("user1 chat1")
                .messageType("TALK")
                .build();
        CreatePubMessageCommand command2 = CreatePubMessageCommand.builder()
                .userId(saveUsers.get(1).getId())
                .roomId(chatRoomId)
                .messageText("user2 chat1")
                .messageType("TALK")
                .build();
        List<ChatMessageDto> chatMessageDtoList = new ArrayList<>();

        //when
        //채팅전송
        chatService.sendMessage(command1);
        Long lastChatMessageId = chatService.sendMessage(command2);
        //최근 채팅 삽입
        ChatMessage chatMessage = chatMessageRepository.findById(lastChatMessageId).get();
        chatMessageDtoList.add(ChatMessageDto.from(chatMessage));
        //이전 채팅 삽입
        LoadChatMessageCommand loadChatMessageCommandcommand = LoadChatMessageCommand.builder().chatMessageId(lastChatMessageId).build();
        chatMessageDtoList.addAll(chatService.loadChatMessage(loadChatMessageCommandcommand));
        //then
        Assertions.assertThat(chatMessageDtoList.size()).isEqualTo(1 + saveUsers.size() * 2);
        Assertions.assertThat(chatMessageDtoList.get(0).getMessageText()).isEqualTo("user2 chat1");
    }

    @Test
    @Transactional
    void 채팅_100건이상에서_조회테스트() {
        List<User> saveUsers = createSaveUsers(2);
        Long chatRoomId = chatService.createChatRoom(createChatRoomCommand("testroom1", saveUsers));
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).get();
        CreatePubMessageCommand lastCommand = null;
        List<ChatMessageDto> firstchatMessageDtoList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            CreatePubMessageCommand command = CreatePubMessageCommand.builder()
                    .userId(saveUsers.get(0).getId())
                    .roomId(chatRoomId)
                    .messageText("user1 chat" + (i + 1))
                    .messageType("TALK")
                    .build();
            Long chatMessageId = chatService.sendMessage(command);
            if (i == 0) {
                ChatMessage firstChatMessage = chatMessageRepository.findById(chatMessageId).get();
                firstchatMessageDtoList.add(ChatMessageDto.from(firstChatMessage));
            }
        }
        for (int i = 0; i < 50; i++) {
            CreatePubMessageCommand command = CreatePubMessageCommand.builder()
                    .userId(saveUsers.get(0).getId())
                    .roomId(chatRoomId)
                    .messageText("user2 chat" + (i + 1))
                    .messageType("TALK")
                    .build();
            lastCommand = command;
            chatService.sendMessage(command);
        }
        List<ChatRoomDto> myChatRooms = chatService.listMyChatRoom(ListMyChatRoomCommand.builder().userId(saveUsers.get(0).getId()).build());
        ChatRoomDto myChatRoom = myChatRooms.get(0);

        LoadChatMessageCommand LoadCommand1 = LoadChatMessageCommand.builder()
                .chatMessageId(myChatRoom.getFirstChatMessage().getId())
                .build();

        //when
        firstchatMessageDtoList.addAll(chatService.loadChatMessage(LoadCommand1));

        ChatMessageDto lastChatMessage1 = firstchatMessageDtoList.get(firstchatMessageDtoList.size() - 1);
        LoadChatMessageCommand LoadCommand2 = LoadChatMessageCommand.builder()
                .chatMessageId(lastChatMessage1.getId())
                .build();
        List<ChatMessageDto> secondChatMessageDtoList = chatService.loadChatMessage(LoadCommand2);
        ChatMessageDto lastChatMessage2 = secondChatMessageDtoList.get(secondChatMessageDtoList.size() - 1);

        for (ChatMessageDto chatMessageDto : firstchatMessageDtoList) {
            System.out.println(chatMessageDto.getMessageText());
        }
        for (ChatMessageDto chatMessageDto : secondChatMessageDtoList) {
            System.out.println(chatMessageDto.getMessageText());
        }
        //then
        Assertions.assertThat(firstchatMessageDtoList.size() + secondChatMessageDtoList.size()).isEqualTo(100 + 50 + 3);
        Assertions.assertThat(lastChatMessage2.getSenderId()).isEqualTo(1L);
    }
}