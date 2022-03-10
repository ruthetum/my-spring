package com.example.webfluxrestexample.service;

import com.example.webfluxrestexample.domain.Member;
import com.example.webfluxrestexample.dto.request.CreateMemberRequest;
import com.example.webfluxrestexample.dto.request.EditMemberRequest;
import com.example.webfluxrestexample.dto.response.MemberResponse;
import com.example.webfluxrestexample.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Flux<MemberResponse> getMembers() {
        List<MemberResponse> memberResponses = memberRepository.findAll().stream()
                .map(MemberResponse::fromEntity)
                .collect(Collectors.toList());
        return Flux.fromIterable(memberResponses);
    }

    public Mono<MemberResponse> getMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException());
        return Mono.just(MemberResponse.fromEntity(member));
    }

    @Transactional
    public Mono<MemberResponse> createMember(CreateMemberRequest request) {
        Member newMember = Member.createMember(request.getName());
        memberRepository.save(newMember);
        return Mono.just(MemberResponse.fromEntity(newMember));
    }

    @Transactional
    public Mono<MemberResponse> editMember(EditMemberRequest request) {
        Member member = memberRepository.findById(request.getId())
                .orElseThrow(() -> new IllegalStateException());
        member.setName(request.getName());
        return Mono.just(MemberResponse.fromEntity(member));
    }

    @Transactional
    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }
}
