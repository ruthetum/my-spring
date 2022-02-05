package com.example.tcpserver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {

    private final RedisTemplate redisTemplate;

    private static final String DEFAULT_RESPONSE = "Q";

    public String processMessage(String message) {
        log.info("Receive message : {}", message);
        return DEFAULT_RESPONSE;
    }
}