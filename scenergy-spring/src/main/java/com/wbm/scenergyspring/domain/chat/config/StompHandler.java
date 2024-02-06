package com.wbm.scenergyspring.domain.chat.config;

import com.wbm.scenergyspring.domain.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor {

    final ChatService chatService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        handlerMessage(accessor.getCommand(), accessor);
        return message;
    }

    private void handlerMessage(StompCommand command, StompHeaderAccessor accessor) {
        long roomId = Long.parseLong(accessor.getFirstNativeHeader("roomId"));
        long userId = Long.parseLong(accessor.getFirstNativeHeader("userId"));
        switch (command) {
            case CONNECT:
                log.info("WS CONNECT");
                chatService.connectRoom(roomId, userId);
                break;
            case DISCONNECT:
                log.info("WS DISCONNECT");
                chatService.disconnectRoom(roomId, userId);
                break;
        }
    }
}