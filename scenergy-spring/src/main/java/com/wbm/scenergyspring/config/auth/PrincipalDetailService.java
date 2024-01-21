package com.wbm.scenergyspring.config.auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.wbm.scenergyspring.domain.user.entity.User;

import com.wbm.scenergyspring.domain.user.repository.UserRepository;

/**
 * 로그인 요청 오면 유저 이름이 있는지 확인
 */

@Service
public class PrincipalDetailService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	// 이것두 username으로 찾아야하는데 repository에 findByUsername 추가해도 되는지??????
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> userEntity = userRepository.findById(Long.parseLong(username));
		if (userEntity.isPresent()) {
			return new PrincipalDetails(userEntity);
		}
		return null;
	}
}
