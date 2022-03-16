package com.example.chatredis.member.dto.response;

import com.example.chatredis.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponse {
    private Long uid;
    private String name;

    public static MemberResponse fromEntity(Member member) {
        MemberResponse response = new MemberResponse();
        response.setUid(member.getId());
        response.setName(member.getName());
        return response;
    }
}
