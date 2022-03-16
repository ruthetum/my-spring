package com.example.chatredis.common;

import com.example.chatredis.chat.domain.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class Init {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit() {
            Room roomA = Room.create("A");
            em.persist(roomA);

            Room roomB = Room.create("B");
            em.persist(roomB);

            Room roomC = Room.create("C");
            em.persist(roomC);
        }
    }
}
