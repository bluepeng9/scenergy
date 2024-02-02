package com.wbm.scenergyspring.domain.chat.repository;

import com.wbm.scenergyspring.domain.chat.dto.ChatMessageDto;
import com.wbm.scenergyspring.domain.chat.entity.ChatMessage;
import com.wbm.scenergyspring.domain.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    @Query("""
            SELECT m FROM ChatMessage m
            JOIN FETCH m.chatRoom cr
            WHERE cr.id = :#{#lc.chatRoomId}
            AND m.createdAt < :#{#lc.createdAt}
            ORDER BY m.createdAt DESC
            """)
    List<ChatMessage> findTop100OrderByCreatedAtDescWhereCurrent(@Param("lc") ChatMessageDto lastChatMessageDto);

    Optional<ChatMessage> findTop1ByChatRoomOrderByCreatedAtDesc(ChatRoom chatRoom);
}
