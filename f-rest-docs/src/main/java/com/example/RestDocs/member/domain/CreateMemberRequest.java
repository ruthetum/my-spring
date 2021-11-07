package com.example.RestDocs.member.domain;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
public class CreateMemberRequest {

    @NotNull
    private String name;

    @Email
    private String email;

    public Member toEntity() {
        return new Member(email, name);
    }
}
