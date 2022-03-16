package com.example.chatredis.chat.controller;

import com.example.chatredis.chat.dto.response.RoomListResponse;
import com.example.chatredis.chat.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;

    @GetMapping
    public ResponseEntity<RoomListResponse> getRooms() {
        return ResponseEntity.ok(roomService.getRooms());
    }

    @GetMapping("/{rid}/access")
    public ResponseEntity<Void> access(
            @PathVariable(value = "rid") String rid,
            @RequestParam(value = "member") String uid
    ) {
        roomService.access(rid, uid);
        return ResponseEntity.ok().build();
    }
}
