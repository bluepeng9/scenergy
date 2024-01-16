package com.wbm.scenergyspring.user.service.command;

import lombok.Data;

@Data
public class CreateUserCommand {
    String email;
    String password;
}
