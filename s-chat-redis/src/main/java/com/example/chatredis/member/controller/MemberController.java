package com.example.chatredis.member.controller;

import com.example.chatredis.member.dto.request.MemberRegisterRequest;
import com.example.chatredis.member.dto.response.MemberResponse;
import com.example.chatredis.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<MemberResponse> register(
            @Valid @RequestBody MemberRegisterRequest request
    ) {
        return ResponseEntity.ok(memberService.register(request));
    }
}
