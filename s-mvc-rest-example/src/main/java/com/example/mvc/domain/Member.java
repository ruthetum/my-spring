package com.example.mvc.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static javax.persistence.CascadeType.ALL;

@Entity
@Table(name = "member")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "member", cascade = ALL)
    private List<Avatar> avatars = new ArrayList<>();

    public static Member createMember(String name) {
        Member member = new Member();
        member.setName(name);
        return member;
    }

    public List<Avatar> getRemainAvatars() {
        return this.getAvatars().stream()
                .filter(a -> a.isStatus())
                .collect(Collectors.toList());
    }

    public void removeAvatar(int sequence) {
        this.getAvatars().get(sequence-1).delete();
        this.getAvatars().remove(sequence);
    }
}
