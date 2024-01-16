package com.wbm.scenergyspring.user.service;

import com.wbm.scenergyspring.user.User;
import com.wbm.scenergyspring.user.repository.UserRepository;
import com.wbm.scenergyspring.user.service.command.CreateUserCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    final UserRepository userRepository;

    @Transactional(readOnly = false)
    public void createUser(CreateUserCommand command) {
        User newUser = User.createNewUser(
                command.getEmail(),
                command.getPassword()
        );
        userRepository.save(newUser);
    }
}
