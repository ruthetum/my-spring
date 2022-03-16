package com.example.mvc.service;

import com.example.mvc.domain.Avatar;
import com.example.mvc.domain.Member;
import com.example.mvc.dto.request.AddAvatarRequest;
import com.example.mvc.dto.request.CreateMemberRequest;
import com.example.mvc.dto.request.EditMemberRequest;
import com.example.mvc.dto.response.MemberResponse;
import com.example.mvc.repository.MemberRepository;
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

    @Transactional
    public MemberResponse addAvatar(AddAvatarRequest request) {
        Member member = memberRepository.findById(request.getId())
                .orElseThrow(() -> new IllegalStateException());
        Avatar.create(member, request.getSrc(), request.getSequence());
        return MemberResponse.fromEntity(member);
    }

    @Transactional
    public void removeAvatar(Long id, int seq) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException());
        member.removeAvatar(seq);
    }
}
