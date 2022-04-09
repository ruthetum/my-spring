package com.example.springbatch.core.service;

import com.example.springbatch.dto.PlayerDto;
import com.example.springbatch.dto.PlayerSalaryDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Year;

import static org.mockito.Mockito.*;

public class PlayerSalaryServiceTest {

    private PlayerSalaryService playerSalaryService;

    @BeforeEach
    public void setup() {
        playerSalaryService = new PlayerSalaryService();
    }

    @Test
    public void calSalary() {
        // given
        Year mockYear = mock(Year.class);
        when(mockYear.getValue()).thenReturn(2022);
        mockStatic(Year.class).when(Year::now).thenReturn(mockYear);

        PlayerDto mockPlayer = mock(PlayerDto.class);
        when(mockPlayer.getBirthYear()).thenReturn(1980);

        // when
        PlayerSalaryDto result = playerSalaryService.calSalary(mockPlayer);

        // then
        Assertions.assertEquals(result.getSalary(), 4200000);
    }
}
