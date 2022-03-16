package com.example.chatredis.chat.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "message")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private String id;

    private String rid;

    private String uid;

    private String content;

    private LocalDateTime time;

    public static Message create(String rid, String uid, String content, LocalDateTime time){
        Message message = new Message();
        message.setId(UUID.randomUUID().toString());
        message.setRid(rid);
        message.setUid(uid);
        message.setContent(content);
        message.setTime(time);
        return message;
    }
}
