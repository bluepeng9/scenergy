package com.wbm.scenergyspring.domain.user.service.command;

import java.util.List;

import lombok.Builder;
import com.wbm.scenergyspring.domain.user.entity.Gender;

import lombok.Data;

@Data
@Builder
public class CreateUserCommand {
    String email;
    String password;
    String nickname;
    String username;
    Gender gender;
}
