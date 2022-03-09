package com.example.webfluxrestexample.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
@AllArgsConstructor
public class CreateMemberRequest {
    @NotEmpty
    private String name;
}
