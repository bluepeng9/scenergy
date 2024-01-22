package com.wbm.scenergyspring.domain.user.entity;

import java.sql.Timestamp;

import com.wbm.scenergyspring.global.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
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
	String gender;

	@Builder
	public User(String username, String password, String email, String gender) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.gender = gender;
	}


	public static User createNewUser(
		String email,
		String password,
		String username,
		String gender,
		String nickname
	) {
		User user = new User();
		user.email = email;
		user.password = password;
		user.username = username;
		user.gender = gender;
		user.nickname = nickname;
		return user;
	}
}
