package com.example.RestDocs.member.domain;

import lombok.Getter;

@Getter
public class MemberResponse {
    private final Long id;
    private final String name;
    private final String email;

    public MemberResponse(final Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.email = member.getEmail();
    }
}
