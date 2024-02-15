package com.wbm.scenergyspring.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.wbm.scenergyspring.domain.chat.entity.UnreadMessage;
import com.wbm.scenergyspring.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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
	@JsonManagedReference(value = "user-unread_messages")
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	List<UnreadMessage> unreadMessages;
	@Column(length = 3000)
	String url;
	String bio;

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
		user.unreadMessages = new ArrayList<>();
		return user;
	}

	public void updateUrl(String url) {
		this.url = url;
	}
	public void updateBio(String bio) {this.bio = bio;}

	public void updateUserName(String name) {
		this.username = name;
	}

	public void updateNickname(String name) {
		this.nickname = name;
	}

}
