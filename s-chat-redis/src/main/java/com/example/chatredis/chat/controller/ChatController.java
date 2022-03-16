package com.example.chatredis.chat.controller;

import com.example.chatredis.chat.dto.request.SendMessageRequest;
import com.example.chatredis.chat.service.ChatService;
import com.example.chatredis.chat.service.RedisPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final RedisPublisher redisPublisher;

    @MessageMapping("/chat/message")
    public void sendMessage(SendMessageRequest request) {
        redisPublisher.sendMessage(request);
        chatService.saveMessage(request);
    }
}
