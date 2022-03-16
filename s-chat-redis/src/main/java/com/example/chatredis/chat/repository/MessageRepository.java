package com.example.chatredis.chat.repository;

import com.example.chatredis.chat.domain.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MessageRepository extends MongoRepository<Message, String> {
    Optional<Message> findTopByRidOrderByTimeDesc(String rid);
}
