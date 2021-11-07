package com.example.RestDocs.member.contoller;

import com.example.RestDocs.common.PageResponse;
import com.example.RestDocs.member.domain.CreateMemberRequest;
import com.example.RestDocs.member.domain.EditMemberRequest;
import com.example.RestDocs.member.domain.MemberResponse;
import com.example.RestDocs.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    /**
     * 1. Member 단일 조회
     */
    @GetMapping("{id}")
    public MemberResponse getMember(
            @PathVariable Long id
    ) {
        log.info("GET /api/members/{} - Member 단일 조회", id);
        return memberService.getMember(id);
    }

    /**
     * 2. Member 페이징 조회
     */
    @GetMapping
    public PageResponse<MemberResponse> getMembers(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        log.info("GET /api/members - Member 페이징 조회");
        return memberService.getMembers(pageable);
    }

    /**
     * 3. Member 생성
     */
    @PostMapping
    public void createMember(
            @Valid @RequestBody CreateMemberRequest request
    ) {
        log.info("POST /api/members - Member 생성");
        memberService.createMember(request);
    }

    /**
     * 4. Member 수정
     */
    @PatchMapping("{id}")
    public void editMember(
            @PathVariable Long id,
            @Valid @RequestBody EditMemberRequest request
    ) {
        log.info("PATCH /api/members/{} - Member 수정", id);
        memberService.editMember(id, request);
    }

    /**
     * 5. Member 삭제
     */
    @DeleteMapping("{id}")
    public void deleteMember(
            @PathVariable Long id
    ) {
        log.info("DELETE /api/members/{} - Member 삭제", id);
        memberService.deleteMember(id);
    }
}
