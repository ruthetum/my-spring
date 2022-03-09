package com.example.webfluxrestexample.dto.response;

import com.example.webfluxrestexample.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponse {
    private String name;

    public static MemberResponse fromEntity(Member member) {
        return MemberResponse.builder()
                .name(member.getName())
                .build();
    }
}
