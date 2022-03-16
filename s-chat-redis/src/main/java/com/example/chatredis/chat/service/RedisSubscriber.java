package com.example.chatredis.chat.service;

import com.example.chatredis.chat.dto.response.MessageResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisSubscriber implements MessageListener {

    private final ObjectMapper objectMapper;
    private final RedisTemplate redisTemplate;
    private final SimpMessageSendingOperations messageTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String publishMessage =
                    (String) redisTemplate.getStringSerializer().deserialize(message.getBody());

            MessageResponse messageResponse = objectMapper.readValue(publishMessage, MessageResponse.class);
            messageTemplate.convertAndSend("/sub/chat/room/" + messageResponse.getRid(), messageResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

