package com.wbm.scenergyspring.domain.user.service.command;

import lombok.Data;

@Data
public class CreateUserCommand {
    String email;
    String password;
}
