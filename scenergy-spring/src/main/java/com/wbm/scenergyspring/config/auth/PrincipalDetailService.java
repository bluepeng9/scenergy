package com.wbm.scenergyspring.config.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.wbm.scenergyspring.domain.user.entity.User;
import com.wbm.scenergyspring.domain.user.repository.UserRepository;
import com.wbm.scenergyspring.global.exception.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

/**
 * 로그인 요청 오면 유저 이름이 있는지 확인
 */

@Service
@RequiredArgsConstructor
public class PrincipalDetailService implements UserDetailsService {

	final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User userEntity = userRepository.findUserByEmail(username)
			.orElseThrow(() -> new EntityNotFoundException("유저 정보가 없습니다."));

		if (userEntity != null) {
			return new PrincipalDetails(userEntity);
		}
		return null;
	}
}
