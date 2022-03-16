package com.example.chatredis.chat.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MessageResponse {
    private String rid;
    private String uid;
    private String content;
}
