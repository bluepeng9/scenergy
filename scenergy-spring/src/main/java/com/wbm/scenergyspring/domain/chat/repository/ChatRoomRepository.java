package com.wbm.scenergyspring.domain.chat.repository;

import com.wbm.scenergyspring.domain.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    @Query("""
            select cr from ChatRoom cr
            join fetch cr.chatUsers cu
            join fetch cu.user u
            where u.id = :userId          
            """)
    List<ChatRoom> findMyChatRoom(@Param("userId") Long userId);
}
