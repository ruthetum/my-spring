package com.example.chatredis.chat.repository;

import com.example.chatredis.chat.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
