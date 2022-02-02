package com.example.tcpserver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {

    private static final byte[] DEFAULT_RESPONSE = new byte[0];

    public byte[] processMessage(byte[] message) {
        log.info("Receive message : {}", message);
        return DEFAULT_RESPONSE;
    }
}