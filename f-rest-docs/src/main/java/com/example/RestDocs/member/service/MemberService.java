package com.example.RestDocs.member.service;

import com.example.RestDocs.common.PageResponse;
import com.example.RestDocs.member.domain.CreateMemberRequest;
import com.example.RestDocs.member.domain.EditMemberRequest;
import com.example.RestDocs.member.domain.MemberResponse;
import com.example.RestDocs.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberResponse getMember(Long id) {
        return new MemberResponse(memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Not found member")));
    }

    public PageResponse<MemberResponse> getMembers(Pageable pageable) {
        return  new PageResponse<>(memberRepository.findAll(pageable)
                .map(MemberResponse::new));
    }

    @Transactional
    public void createMember(CreateMemberRequest request) {
        memberRepository.save(request.toEntity());
    }

    @Transactional
    public void editMember(Long id, EditMemberRequest request) {
        memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Not found member"))
                .setName(request.getName());
    }

    @Transactional
    public void deleteMember(Long id) {
        memberRepository.delete(
                memberRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Not found member"))
        );
    }
}
