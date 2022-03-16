package com.example.chatredis.chat.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SendMessageRequest {
    private String rid;
    private String uid;
    private String content;
}
