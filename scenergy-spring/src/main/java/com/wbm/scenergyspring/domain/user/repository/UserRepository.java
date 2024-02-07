package com.wbm.scenergyspring.domain.user.repository;

import com.wbm.scenergyspring.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    Optional<User> findByEmail(String userEmail);

    @Query("""
               select u from User u
               where u.nickname like %:word%
            """)
    List<User> searchUsers(@Param("word") String word);

    @Query("""
               select f.to from User u
               join Follow f
               on u = f.from
               where u.id = :userId
            """)
    List<User> searchFollowingAll(@Param("userId") Long userId);

    @Query("""
               select f.to from User u
               join Follow f
               on u = f.from
               where u.id = :userId
               and f.to.nickname like %:word%
            """)
    List<User> searchFollowing(@Param("userId") Long userId, @Param("word") String word);
}
