package com.wbm.scenergyspring.domain.user.controller.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadProfileRequest {

    MultipartFile profile;
    Long userId;

}
