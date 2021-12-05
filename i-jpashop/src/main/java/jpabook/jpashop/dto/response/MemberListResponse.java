package jpabook.jpashop.dto.response;

import jpabook.jpashop.domain.Member;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MemberListResponse {
    List<MemberDetailResponse> members;

    public MemberListResponse(List<Member> members) {
        this.members = members.stream()
                .map(MemberDetailResponse::new)
                .collect(Collectors.toList());
    }
}
