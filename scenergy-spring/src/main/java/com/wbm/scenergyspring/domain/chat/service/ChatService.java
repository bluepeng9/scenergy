package com.wbm.scenergyspring.domain.chat.service;

import com.wbm.scenergyspring.domain.chat.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatService {

    final ChatRepository userRepository;
}
