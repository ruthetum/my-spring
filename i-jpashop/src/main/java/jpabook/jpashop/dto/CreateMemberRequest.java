package jpabook.jpashop.dto;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class CreateMemberRequest {
    @NotEmpty
    private String name;

    public CreateMemberRequest(String name) {
        this.name = name;
    }
}
