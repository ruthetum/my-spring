package com.example.RestDocs;

import com.example.RestDocs.member.domain.Member;
import com.example.RestDocs.member.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class DataSetup implements ApplicationRunner {

    private final MemberRepository memberRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        final List<Member> members = new ArrayList<>();

        members.add(new Member("member1@naver.com", "name1"));
        members.add(new Member("member2@naver.com", "name2"));
        members.add(new Member("member3@naver.com", "name3"));
        members.add(new Member("member4@naver.com", "name4"));

        memberRepository.saveAll(members);
    }
}
