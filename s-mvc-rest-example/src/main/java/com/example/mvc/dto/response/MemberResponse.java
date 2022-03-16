package com.example.mvc.dto.response;

import com.example.mvc.domain.Avatar;
import com.example.mvc.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponse {
    private Long id;
    private String name;
    private List<String> avatars;

    public static MemberResponse fromEntity(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .name(member.getName())
                .avatars(
                        member.getRemainAvatars().stream()
                                .map(Avatar::getSrc)
                                .collect(Collectors.toList()))
                .build();
    }
}
