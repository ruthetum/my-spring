package com.example.chatredis.chat.service;

import com.example.chatredis.chat.domain.Message;
import com.example.chatredis.chat.dto.request.SendMessageRequest;
import com.example.chatredis.chat.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatService {

    private final MessageRepository messageRepository;

    @Transactional
    public void saveMessage(SendMessageRequest request) {
        LocalDateTime now = LocalDateTime.now();

        Message message = Message.create(
                request.getRid(),
                request.getUid(),
                request.getContent(),
                now
        );
        messageRepository.save(message);
    }
}
