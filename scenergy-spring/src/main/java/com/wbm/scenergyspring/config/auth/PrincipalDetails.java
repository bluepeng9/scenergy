package com.wbm.scenergyspring.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.wbm.scenergyspring.domain.user.entity.User;

/**
 * Security Session => authentication => UserDetails
 */
public class PrincipalDetails implements UserDetails {

	private User user;

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

	// 유저아이디 컬럼 만들어도 되나????????
	@Override
	public String getUsername() {
		return String.valueOf(user.getId());
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
}
