package jpabook.jpashop.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class UpdateMemberRequest {
    @NotEmpty
    private String name;

    public UpdateMemberRequest(String name) {
        this.name = name;
    }
}
