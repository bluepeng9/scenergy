package com.wbm.scenergyspring.domain.chat.repository;

import com.wbm.scenergyspring.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<User, Long> {
}
