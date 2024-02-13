package com.wbm.scenergyspring.domain.user.service.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateUserInfoCommand {

    Long userId;
    String userName;
    String nickname;

}
