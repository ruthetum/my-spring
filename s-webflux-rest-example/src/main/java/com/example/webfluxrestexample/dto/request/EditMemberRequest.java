package com.example.webfluxrestexample.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
@AllArgsConstructor
public class EditMemberRequest {
    @NotEmpty
    private Long id;

    @NotEmpty
    private String name;
}
