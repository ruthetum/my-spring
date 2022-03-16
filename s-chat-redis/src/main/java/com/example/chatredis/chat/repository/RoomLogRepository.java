package com.example.chatredis.chat.repository;

import com.example.chatredis.chat.domain.RoomLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoomLogRepository extends MongoRepository<RoomLog, String> {
}
