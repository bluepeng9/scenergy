package com.wbm.scenergyspring.domain.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wbm.scenergyspring.domain.user.entity.User;
import com.wbm.scenergyspring.domain.user.repository.UserRepository;
import com.wbm.scenergyspring.domain.user.service.command.CreateUserCommand;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

	final UserRepository userRepository;

	@Transactional(readOnly = false)
	public Long createUser(CreateUserCommand command) {
		User newUser = User.createNewUser(
			command.getEmail(),
			command.getPassword(),
			command.getUsername(),
			command.getGender(),
			command.getNickname()
		);
		return userRepository.save(newUser).getId();
	}

}
