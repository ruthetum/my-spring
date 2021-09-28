package org.example.dmaker.dto;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class DevDto {
    String name;
    Integer age;
    LocalDateTime startAt;

    public void printLog() {
        log.info(getName());
    }
}
