package com.wbm.scenergyspring.domain.chat.repository;

import com.wbm.scenergyspring.domain.chat.entity.ChatRoom;
import com.wbm.scenergyspring.domain.chat.entity.ChatUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    @Query("""
            select cr from ChatRoom cr
            join fetch cr.chatUsers cu
            join fetch cu.user u
            where u.id = :userId          
            """)
    List<ChatRoom> findMyChatRoomByUserId(@Param("userId") Long userId);


    @Query("""
            select distinct cu from ChatRoom cr
            join cr.chatUsers cu
            join cu.user u
            where true 
                and u.id = :userId
                and cr.id = :roomId        
            """)
    Optional<ChatUser> findChatUserByIdAndUser(@Param("roomId") Long roomId, @Param("userId") Long userId);

    @Query("""
            select cr from ChatRoom cr
            join cr.chatUsers cu
            where true 
                and cr.status = 0
                and (cu.user.id = :user1Id or cu.user.id = :user2Id)
            group by cr.id, cr.status, cr.createdAt, cr.updatedAt, cr.name
            having count(cr.status) > 1
            """)
    Optional<ChatRoom> findCommonChatRoom(@Param("user1Id") Long user1, @Param("user2Id") Long user2);

    @Query("""
            select cu from ChatRoom cr
            join cr.chatUsers cu
            where cr.id = :chatRoomId
            """)
    Optional<List<ChatUser>> findChatRoomUsers(@Param("chatRoomId") Long chatRoomId);

}
