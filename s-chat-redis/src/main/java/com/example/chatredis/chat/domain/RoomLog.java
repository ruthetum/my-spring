package com.example.chatredis.chat.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Entity
@Getter
@Setter
@Table(name = "room_log")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomLog {

    @Id
    private String rid;

    @Type(type = "json")
    private Map<String, LocalDateTime> access = new HashMap<>();

    public static RoomLog create(
            String rid,
            String uid,
            LocalDateTime time
    ) {
        RoomLog log = new RoomLog();
        log.setRid(rid);
        Map<String, LocalDateTime> access = new HashMap<>();
        access.put(uid, time);
        log.setAccess(access);
        return log;
    }

    public void update(
            String uid,
            LocalDateTime time
    ) {
        Map<String, LocalDateTime> access = this.getAccess();
        access.put(uid, time);
    }
}
