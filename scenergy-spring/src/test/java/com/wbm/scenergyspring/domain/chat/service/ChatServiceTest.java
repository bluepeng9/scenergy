package com.wbm.scenergyspring.domain.chat.service;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.wbm.scenergyspring.IntegrationTest;
import com.wbm.scenergyspring.domain.chat.dto.ChatMessageDto;
import com.wbm.scenergyspring.domain.chat.dto.ChatRoomDto;
import com.wbm.scenergyspring.domain.chat.dto.RedisChatRoomDto;
import com.wbm.scenergyspring.domain.chat.entity.ChatMessage;
import com.wbm.scenergyspring.domain.chat.entity.ChatRoom;
import com.wbm.scenergyspring.domain.chat.repository.ChatMessageRepository;
import com.wbm.scenergyspring.domain.chat.repository.ChatRoomRepository;
import com.wbm.scenergyspring.domain.chat.repository.ChatUserRepository;
import com.wbm.scenergyspring.domain.chat.repository.RedisChatRepository;
import com.wbm.scenergyspring.domain.chat.service.command.CreateChatRoomCommand;
import com.wbm.scenergyspring.domain.chat.service.command.CreatePubMessageCommand;
import com.wbm.scenergyspring.domain.chat.service.command.InviteChatRoomCommand;
import com.wbm.scenergyspring.domain.chat.service.command.ListMyChatRoomCommand;
import com.wbm.scenergyspring.domain.chat.service.command.LoadChatMessageCommand;
import com.wbm.scenergyspring.domain.chat.service.command.RenameChatRoomCommand;
import com.wbm.scenergyspring.domain.user.entity.User;
import com.wbm.scenergyspring.domain.user.repository.UserRepository;
import com.wbm.scenergyspring.util.UserGenerator;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@SpringBootTest
class ChatServiceTest extends IntegrationTest {

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
    @Autowired
    RedisChatRepository redisChatRepository;

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
        List<ChatRoom> myChatRoom1 = chatRoomRepository.findMyChatRoomByUserId(saveUsers.get(0).getId());
        List<ChatRoom> myChatRoom2 = chatRoomRepository.findMyChatRoomByUserId(saveUsers.get(1).getId());
        List<ChatRoom> myChatRoom3 = chatRoomRepository.findMyChatRoomByUserId(saveUsers.get(2).getId());
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
        //given
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
                .chatMessageId(myChatRoom.getRecentChatMessage().getId())
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

    @Test
    @Transactional
    void 채팅_미접속유저_테스트() {
        //given
        //room making
        List<User> saveUsers = createSaveUsers(3);
        Long chatRoomId = chatService.createChatRoom(createChatRoomCommand("testroom1", saveUsers));
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).get();
        String sessionIdUser1 = "session1-c";
        String sessionIdUser2 = "session2-c";
        String sessionIdUser3 = "session3-c";
        //when
        chatService.connectRoom(chatRoomId, saveUsers.get(0).getId(), sessionIdUser1);
        List<Long> offlineMember1 = redisChatRepository.getOfflineMembers(RedisChatRoomDto.from(chatRoom));
        chatService.connectRoom(chatRoomId, saveUsers.get(1).getId(), sessionIdUser2);
        List<Long> offlineMember2 = redisChatRepository.getOfflineMembers(RedisChatRoomDto.from(chatRoom));
        chatService.disconnectRoom(sessionIdUser1);
        List<Long> offlineMember3 = redisChatRepository.getOfflineMembers(RedisChatRoomDto.from(chatRoom));
        //then
        Assertions.assertThat(offlineMember1.size()).isEqualTo(saveUsers.size() - 1);
        Assertions.assertThat(offlineMember2.size()).isEqualTo(saveUsers.size() - 2);
        Assertions.assertThat(offlineMember2.get(0)).isEqualTo(saveUsers.get(2).getId());
        Assertions.assertThat(offlineMember3.size()).isEqualTo(saveUsers.size() - 1);
    }

    @Test
    @Transactional
    void 채팅_안읽은_메시지_테스트() {
        //given
        List<User> saveUsers = createSaveUsers(3);
        User user1 = saveUsers.get(0);
        User user2 = saveUsers.get(1);
        User user3 = saveUsers.get(2);
        Long chatRoomId = chatService.createChatRoom(createChatRoomCommand("testroom1", saveUsers));
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).get();

        int sendCount = 5;

        String sessionIdUser1 = "session1-u";
        String sessionIdUser2 = "session2-u";
        String sessionIdUser3 = "session3-u";
        chatService.connectRoom(chatRoomId, user1.getId(), sessionIdUser1);
        //when
        sendMessage(user1, chatRoomId, sendCount);
        int unreadMessageCount1 = redisChatRepository.getUnreadMessageCount(chatRoomId, user1.getId());
        int unreadMessageCount2 = redisChatRepository.getUnreadMessageCount(chatRoomId, user2.getId());
        int unreadMessageCount3 = redisChatRepository.getUnreadMessageCount(chatRoomId, user3.getId());

        chatService.connectRoom(chatRoomId, user2.getId(), sessionIdUser2);

        sendMessage(user2, chatRoomId, sendCount);
        int unreadMessageCount4 = redisChatRepository.getUnreadMessageCount(chatRoomId, user1.getId());
        int unreadMessageCount5 = redisChatRepository.getUnreadMessageCount(chatRoomId, user2.getId());
        int unreadMessageCount6 = redisChatRepository.getUnreadMessageCount(chatRoomId, user3.getId());
        //then
        Assertions.assertThat(unreadMessageCount1).isEqualTo(0);
        Assertions.assertThat(unreadMessageCount2).isEqualTo(sendCount + 4);
        Assertions.assertThat(unreadMessageCount3).isEqualTo(sendCount + 4);

        Assertions.assertThat(unreadMessageCount4).isEqualTo(0);
        Assertions.assertThat(unreadMessageCount5).isEqualTo(0);
        Assertions.assertThat(unreadMessageCount6).isEqualTo(sendCount * 2 + 4);
    }

    @Test
    @Transactional
    void 유저세션_저장_테스트() {
        List<User> saveUsers = createSaveUsers(3);
        User user1 = saveUsers.get(0);
        User user2 = saveUsers.get(1);
        User user3 = saveUsers.get(2);
        Long chatRoomId = chatService.createChatRoom(createChatRoomCommand("testroom1", saveUsers));
        String sessionId = "sessionId";
        chatService.connectRoom(chatRoomId, user1.getId(), sessionId);
        chatService.disconnectRoom(sessionId);
    }

    private void sendMessage(User user, Long chatRoomId, int user1SendCount) {
        for (int i = 0; i < user1SendCount; i++) {
            CreatePubMessageCommand command = CreatePubMessageCommand.builder()
                    .userId(user.getId())
                    .roomId(chatRoomId)
                    .messageText(user.getUsername() + (i + 1))
                    .messageType("TALK")
                    .build();
            Long chatMessageId = chatService.sendMessage(command);
        }
    }


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

}