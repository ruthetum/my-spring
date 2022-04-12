package com.example.housebatch.core.entity;

import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Lawd extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String lawdCd;

    @Column(nullable = false)
    private String lawdDong;

    @Column(nullable = false)
    private Boolean exist;

    public static Lawd create(String lawdCd, String lawdDong, Boolean exist) {
        Lawd lawd = new Lawd();
        lawd.setLawdCd(lawdCd);
        lawd.setLawdDong(lawdDong);
        lawd.setExist(exist);
        return lawd;
    }
}
