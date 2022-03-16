package com.example.chatredis.chat.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RoomListResponse {
    private List<RoomResponse> rooms;
}
