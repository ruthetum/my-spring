package com.example.webfluxrestexample.controller;

import com.example.webfluxrestexample.dto.request.CreateMemberRequest;
import com.example.webfluxrestexample.dto.request.EditMemberRequest;
import com.example.webfluxrestexample.dto.response.MemberResponse;
import com.example.webfluxrestexample.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public Flux<MemberResponse> getMembers() {
        return memberService.getMembers();
    }

    @GetMapping("/{id}")
    public Mono<MemberResponse> getMember(@PathVariable("id") Long id) {
        return memberService.getMember(id);
    }

    @PostMapping
    public Mono<MemberResponse> createMember(@Valid @RequestBody CreateMemberRequest request) {
        return memberService.createMember(request);
    }

    @PutMapping
    public Mono<MemberResponse> editMember(@Valid @RequestBody EditMemberRequest request) {
        return memberService.editMember(request);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteMember(@PathVariable("id") Long id) {
        memberService.deleteMember(id);
        return Mono.empty();
    }
}
