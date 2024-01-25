package com.wbm.scenergyspring.domain.user.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
}
