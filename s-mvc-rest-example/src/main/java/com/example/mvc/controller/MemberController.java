package com.example.mvc.controller;

import com.example.mvc.dto.request.CreateMemberRequest;
import com.example.mvc.dto.request.EditMemberRequest;
import com.example.mvc.dto.response.MemberResponse;
import com.example.mvc.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<List<MemberResponse>> getMembers() {
        log.info("GET /members");
        return ResponseEntity.ok(memberService.getMembers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> getMember(@PathVariable("id") Long id) {
        log.info("GET /members/{}", id);
        return ResponseEntity.ok(memberService.getMember(id));
    }

    @PostMapping
    public ResponseEntity<MemberResponse> createMember(@Valid @RequestBody CreateMemberRequest request) {
        log.info("POST /members");
        return ResponseEntity.ok(memberService.createMember(request));
    }

    @PutMapping
    public ResponseEntity<MemberResponse> editMember(@Valid @RequestBody EditMemberRequest request) {
        log.info("PUT /members");
        return ResponseEntity.ok(memberService.editMember(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable("id") Long id) {
        log.info("DELETE /members/{}", id);
        memberService.deleteMember(id);
        return ResponseEntity.ok().build();
    }
}
