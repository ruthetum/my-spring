package jpabook.jpashop.dto.response;

import jpabook.jpashop.domain.Member;
import lombok.Getter;

@Getter
public class MemberDetailResponse {
    private Long id;
    private String name;

    public MemberDetailResponse(Member member) {
        this.id = member.getId();
        this.name = member.getName();
    }
}
