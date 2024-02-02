package com.wbm.scenergyspring.domain.chat.repository;

import com.wbm.scenergyspring.domain.chat.entity.ChatRoom;
import com.wbm.scenergyspring.domain.chat.entity.ChatUser;
import com.wbm.scenergyspring.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatUserRepository extends JpaRepository<ChatUser, Long> {
    Optional<ChatUser> findByChatRoomAndUser(ChatRoom chatRoom, User user);

    @Query("""
            select u from ChatUser cu
            join fetch User u
            on cu.user = u
            where cu.chatRoom = :cr
            """)
    List<User> findAllByChatRoom(@Param("cr") ChatRoom chatRoom);
}
