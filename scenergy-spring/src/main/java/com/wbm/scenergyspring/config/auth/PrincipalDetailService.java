package com.wbm.scenergyspring.config.auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.wbm.scenergyspring.domain.user.entity.User;

import com.wbm.scenergyspring.domain.user.repository.UserRepository;

import lombok.Data;

/**
 * 로그인 요청 오면 유저 이름이 있는지 확인
 */

@Service
public class PrincipalDetailService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("PrincipalDetails loadUserByUsername 메서드 실행");
		User userEntity = userRepository.findByUsername(username);
		if (userEntity != null) {
			System.out.println("userEntity"+userEntity);
			return new PrincipalDetails(userEntity);
		}
		return null;
	}
}
