package com.example.scenergynotification.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.scenergynotification.domain.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
