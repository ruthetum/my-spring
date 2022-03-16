package com.example.chatredis.chat.service;

import com.example.chatredis.chat.dto.request.SendMessageRequest;
import com.example.chatredis.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisPublisher {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ChatRoomRepository chatRoomRepository;

    public void sendMessage(SendMessageRequest request) {
        String rid = request.getRid();
        chatRoomRepository.enterRoom(rid);
        redisTemplate.convertAndSend(chatRoomRepository.getTopic(rid).getTopic(), request);
    }
}