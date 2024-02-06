package com.wbm.scenergyspring.domain.user.service.command;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class UploadProfileCommand {

    MultipartFile multipartFile;
    Long userId;

}
