package com.wbm.scenergyspring.domain.user.entity;

import com.wbm.scenergyspring.global.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
//user 는 예약어이므로 테이블 이름을 변경해야 함
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@Column(unique = true)
	String email;
	String password;
	@Column(unique = true)
	String nickname;


	public static User createNewUser(
		String email,
		String password,
		String nickname
	) {
		User user = new User();
		user.email = email;
		user.password = password;
		user.nickname = nickname;
		return user;
	}
}
