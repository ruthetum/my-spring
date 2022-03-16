package com.example.chatredis.chat.repository;

import com.example.chatredis.chat.service.RedisSubscriber;
import com.example.chatredis.chat.domain.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class ChatRoomRepository {

    private final RedisMessageListenerContainer redisMessageListenerContainer;
    private final RedisSubscriber redisSubscriber;
    private Map<String, ChannelTopic> topics;

    @PostConstruct
    private void init() {
        topics = new HashMap<>();
    }

    public void enterRoom(String id) {
        ChannelTopic topic = topics.get(id);
        if (Objects.isNull(topic)) {
            topic = new ChannelTopic(id);
            redisMessageListenerContainer.addMessageListener(redisSubscriber, topic);
            topics.put(id, topic);
        }
    }

    public ChannelTopic getTopic(String id) {
        return topics.get(id);
    }
}
