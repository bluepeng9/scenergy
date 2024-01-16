package com.wbm.scenergyspring.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
//user 는 예약어이므로 테이블 이름을 변경해야 함
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String email;
    String password;

    public static User createNewUser(
            String email,
            String password
    ) {
        User user = new User();
        user.email = email;
        user.password = password;
        return user;
    }
}
