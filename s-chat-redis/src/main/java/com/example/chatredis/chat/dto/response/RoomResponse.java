package com.example.chatredis.chat.dto.response;

import com.example.chatredis.chat.domain.Room;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponse {
    private Long rid;
    private String name;

    public static RoomResponse fromEntity(Room room) {
        RoomResponse response = new RoomResponse();
        response.setRid(room.getId());
        response.setName(room.getName());
        return response;
    }
}
