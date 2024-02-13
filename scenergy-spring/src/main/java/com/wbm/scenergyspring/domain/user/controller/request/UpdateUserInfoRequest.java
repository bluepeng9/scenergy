package com.wbm.scenergyspring.domain.user.controller.request;

import lombok.Data;

@Data
public class UpdateUserInfoRequest {

    Long userId;
    String userName;
    String nickname;

}
