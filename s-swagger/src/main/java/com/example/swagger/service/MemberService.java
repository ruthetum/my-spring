package com.example.swagger.service;

import com.example.swagger.domain.Member;
import com.example.swagger.dto.request.CreateMemberRequest;
import com.example.swagger.dto.request.EditMemberRequest;
import com.example.swagger.dto.response.MemberResponse;
import com.example.swagger.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public List<MemberResponse> getMembers() {
        List<MemberResponse> memberResponses = memberRepository.findAll().stream()
                .map(MemberResponse::fromEntity)
                .collect(Collectors.toList());
        return memberResponses;
    }

    public MemberResponse getMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException());
        return MemberResponse.fromEntity(member);
    }

    @Transactional
    public MemberResponse createMember(CreateMemberRequest request) {
        Member newMember = Member.createMember(request.getName());
        memberRepository.save(newMember);
        return MemberResponse.fromEntity(newMember);
    }

    @Transactional
    public MemberResponse editMember(EditMemberRequest request) {
        Member member = memberRepository.findById(request.getId())
                .orElseThrow(() -> new IllegalStateException());
        member.setName(request.getName());
        return MemberResponse.fromEntity(member);
    }

    @Transactional
    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }
}
