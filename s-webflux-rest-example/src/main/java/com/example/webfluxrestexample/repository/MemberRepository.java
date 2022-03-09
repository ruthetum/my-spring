package com.example.webfluxrestexample.repository;

import com.example.webfluxrestexample.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Flux<Member> findAllMembers();
    Mono<Member> findMemberById(Long Id);
}
