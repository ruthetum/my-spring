package org.example.dmaker.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dmaker.dto.CreateDeveloperDto;
import org.example.dmaker.service.DmakerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DmakerController {
    private final DmakerService dmakerService;

    @GetMapping("/developers")
    public List<String> getAllDevelopers() {
        log.info("GET /developers HTTP/1.1");

        return Arrays.asList("Dong", "Tom", "Olaf");
    }

    @PostMapping("/developers")
    public List<String> createDeveloper(
            @RequestBody CreateDeveloperDto.Request request
            ) {
        log.info("POST /developers HTTP/1.1 - request : " + request);

        dmakerService.createDeveloper(request);

        return Collections.singletonList("Olaf");
    }
}
