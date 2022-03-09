package com.example.webfluxrestexample.config;

import com.example.webfluxrestexample.domain.Member;
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
            Member memberA = Member.createMember("memberA");
            em.persist(memberA);

            Member memberB = Member.createMember("memberB");
            em.persist(memberB);

            Member memberC = Member.createMember("memberC");
            em.persist(memberC);
        }
    }
}
