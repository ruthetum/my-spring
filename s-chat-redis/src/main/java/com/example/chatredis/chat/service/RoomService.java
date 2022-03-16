package com.example.chatredis.chat.service;

import com.example.chatredis.chat.domain.Message;
import com.example.chatredis.chat.domain.Room;
import com.example.chatredis.chat.domain.RoomLog;
import com.example.chatredis.chat.dto.response.RoomListResponse;
import com.example.chatredis.chat.dto.response.RoomResponse;
import com.example.chatredis.chat.repository.MessageRepository;
import com.example.chatredis.chat.repository.RoomLogRepository;
import com.example.chatredis.chat.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomService {

    private final RoomRepository roomRepository;
    private final MessageRepository messageRepository;
    private final RoomLogRepository roomLogRepository;

    public RoomListResponse getRooms() {
        List<Room> rooms = roomRepository.findAll();
        return new RoomListResponse(
                rooms.stream()
                        .map(RoomResponse::fromEntity)
                        .collect(Collectors.toList()));
    }

    @Transactional
    public void access(String rid, String uid) {
        LocalDateTime now = LocalDateTime.now();
        Optional<RoomLog> roomLog = roomLogRepository.findById(rid);
        if (Objects.isNull(roomLog)) {
            RoomLog log = RoomLog.create(rid, uid, now);
            roomLogRepository.save(log);
            return;
        }
        roomLog.get().update(uid, now);
    }
}
