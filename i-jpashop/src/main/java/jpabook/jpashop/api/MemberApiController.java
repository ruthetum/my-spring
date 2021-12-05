package jpabook.jpashop.api;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.dto.request.CreateMemberRequest;
import jpabook.jpashop.dto.request.UpdateMemberRequest;
import jpabook.jpashop.dto.response.CreateMemberResponse;
import jpabook.jpashop.dto.response.MemberDetailResponse;
import jpabook.jpashop.dto.response.MemberListResponse;
import jpabook.jpashop.dto.response.UpdateMemberResponse;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    /**
     * 회원 등록
     */
    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {

        Member member = new Member();
        member.setName(request.getName());

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    /**
     * 회원 수정
     */
    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateMemberRequest request
    ) {
        memberService.update(id, request.getName());
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    /**
     * 회원 조회
     */
    @GetMapping("/api/v2/members")
    public MemberListResponse getMemberList() {
        List<Member> members = memberService.findMembers();
        return new MemberListResponse(members);
    }

    @GetMapping("/api/v2/members/{id}")
    public MemberDetailResponse getMemberDetail(
            @PathVariable("id") Long id
    ) {
        Member findMember = memberService.findOne(id);
        return new MemberDetailResponse(findMember);
    }
}
