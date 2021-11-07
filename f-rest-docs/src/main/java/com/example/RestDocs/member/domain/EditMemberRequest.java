package com.example.RestDocs.member.domain;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class EditMemberRequest {

    @NotNull
    private String name;
}
