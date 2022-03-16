package com.example.chatredis.member.service;

import com.example.chatredis.member.domain.Member;
import com.example.chatredis.member.dto.request.MemberRegisterRequest;
import com.example.chatredis.member.dto.response.MemberResponse;
import com.example.chatredis.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberResponse register(MemberRegisterRequest request) {
        String name = request.getName();
        isDuplicatedName(name);
        Member member = Member.create(name);
        memberRepository.save(member);
        return MemberResponse.fromEntity(member);
    }

    private void isDuplicatedName(String name) {
        memberRepository.findByName(name)
                .ifPresent((m -> { throw new IllegalStateException(); }));
    }
}
