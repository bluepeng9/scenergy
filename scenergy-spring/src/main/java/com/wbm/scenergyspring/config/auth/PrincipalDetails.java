package com.wbm.scenergyspring.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.wbm.scenergyspring.domain.user.entity.User;

import lombok.Data;

/**
 * Security Session => authentication => UserDetails
 * 일반로그인, 소셜로그인 모두 PrincipalDeatails로
 * 접근하기 위해 둘 다 상속
 */
@Data
public class PrincipalDetails implements UserDetails, OAuth2User {

	private User user;

	// 소셜 로그인시 가져온 사용자 정보들
	public PrincipalDetails(User user) {
		this.user = user;
	}

	private Map<String, Object> attributes;
	// 소셜 로그인 생성자
	public PrincipalDetails(User user, Map<String, Object> attributes) {
		this.user = user;
		this.attributes = attributes;
	}

	// 유저 권한
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collect = new ArrayList<>();
		collect.add(new GrantedAuthority() {
			@Override
			public String getAuthority() {
				return "USER";
			}
		});
		return collect;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {

		return user.getUsername();
	}

	// 계정 만료여부
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	// 계정 활성화여부
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	// 비밀번호 1년지났는지
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	// 계정활성화
	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return null;
	}

	@Override
	public String getName() {
		return null;
	}
}
