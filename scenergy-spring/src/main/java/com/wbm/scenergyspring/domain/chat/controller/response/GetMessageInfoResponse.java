package com.wbm.scenergyspring.domain.chat.controller.response;

import com.wbm.scenergyspring.domain.chat.dto.ChatMessageDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetMessageInfoResponse {
    ChatMessageDto chatMessageDto;
}
