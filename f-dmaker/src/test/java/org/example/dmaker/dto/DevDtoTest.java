package org.example.dmaker.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DevDtoTest {
    @Test
    void test() {
        DevDto devDto = DevDto.builder()
                            .name("dong")
                            .age(25)
                            .startAt(LocalDateTime.now())
                            .build();

        System.out.println(devDto);
    }
}