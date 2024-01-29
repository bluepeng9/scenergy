package com.wbm.scenergyspring.domain.user.entity;

import com.wbm.scenergyspring.global.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
	String username;
	@Enumerated(EnumType.STRING)
	Gender gender;
	@Enumerated(EnumType.STRING)
	Role role;

	public static User createNewUser(
		String email,
		String password,
		String username,
		Gender gender,
		String nickname
	) {
		User user = new User();
		user.email = email;
		user.password = password;
		user.username = username;
		user.gender = gender;
		user.nickname = nickname;
		user.role = Role.user;
		return user;
	}
}
