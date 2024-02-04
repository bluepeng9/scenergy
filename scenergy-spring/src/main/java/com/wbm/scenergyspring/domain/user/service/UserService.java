package com.wbm.scenergyspring.domain.user.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wbm.scenergyspring.domain.user.entity.User;
import com.wbm.scenergyspring.domain.user.repository.UserRepository;
import com.wbm.scenergyspring.domain.user.service.command.CreateUserCommand;
import com.wbm.scenergyspring.domain.user.service.commanresult.FindUserCommandResult;
import com.wbm.scenergyspring.global.exception.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

	final BCryptPasswordEncoder bCryptPasswordEncoder;
	final UserRepository userRepository;

	@Transactional(readOnly = false)
	public Long createUser(CreateUserCommand command) {
		User newUser = User.createNewUser(
			command.getEmail(),
			bCryptPasswordEncoder.encode(command.getPassword()),
			command.getUsername(),
			command.getGender(),
			command.getNickname()
		);
		return userRepository.save(newUser).getId();
	}

	@Transactional(readOnly = false)
	public Long deleteUser(String password, String username) {
		User user = userRepository.findByUsername(username);
		if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
			userRepository.delete(user);
		}
		return 1L;
	}

	public FindUserCommandResult findUser(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(
			() -> new EntityNotFoundException("해당 사용자를 찾을 수 없습니다.")
		);

		return FindUserCommandResult.builder()
			.nickname(user.getNickname())
			.build();
	}
}
