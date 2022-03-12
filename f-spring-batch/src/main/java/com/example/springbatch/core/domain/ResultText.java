package com.example.springbatch.core.domain;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter
@Setter
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "result_text")
public class ResultText {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String text;

    public static ResultText create(String text) {
        ResultText resultText = new ResultText();
        resultText.setText(text);
        return resultText;
    }
}
