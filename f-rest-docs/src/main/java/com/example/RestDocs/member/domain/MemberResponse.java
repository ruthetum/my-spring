package com.example.RestDocs.member.domain;

import lombok.Getter;

@Getter
public class MemberResponse {
    private final String name;
    private final String email;

    public MemberResponse(final Member member) {
        this.name = member.getName();
        this.email = member.getEmail();
    }
}
