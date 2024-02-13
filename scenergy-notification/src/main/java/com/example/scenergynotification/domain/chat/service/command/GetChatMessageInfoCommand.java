package com.example.scenergynotification.domain.chat.service.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetChatMessageInfoCommand {

	Long chatMessageId;
}
