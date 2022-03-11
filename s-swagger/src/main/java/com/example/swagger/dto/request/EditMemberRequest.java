package com.example.swagger.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EditMemberRequest {
    @NotNull
    private Long id;
    @NotNull
    private String name;
}
