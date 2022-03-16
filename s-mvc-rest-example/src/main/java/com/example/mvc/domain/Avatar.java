package com.example.mvc.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "avatar")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Avatar {

    @Id
    @GeneratedValue
    @Column(name = "avatar_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String src;

    private int sequence;

    private boolean status;

    private void setMember(Member member) {
        this.member = member;
        member.getAvatars().add(this);
    }

    public static Avatar create(
            Member member,
            String src,
            int sequence
    ) {
        Avatar avatar = new Avatar();
        avatar.setMember(member);
        avatar.setSrc(src);
        avatar.setSequence(sequence);
        avatar.setStatus(true);
        return avatar;
    }

    public void delete() {
        this.status = false;
    }
}
