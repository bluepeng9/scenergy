package com.wbm.scenergyspring.domain.chat.controller.response;

import com.wbm.scenergyspring.domain.chat.entity.ChatMessageDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LoadChatMessageResponse {
    List<ChatMessageDto> messageList;
}
