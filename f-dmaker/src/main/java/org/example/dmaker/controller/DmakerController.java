package org.example.dmaker.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dmaker.dto.CreateDeveloperDto;
import org.example.dmaker.dto.DeveloperDetailDto;
import org.example.dmaker.dto.DeveloperDto;
import org.example.dmaker.service.DmakerService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DmakerController {
    private final DmakerService dmakerService;

    @GetMapping("/developers")
    public List<DeveloperDto> getAllDevelopers() {
        log.info("GET /developers HTTP/1.1");

        return dmakerService.getAllDevelopers();
    }

    @GetMapping("/developers/{memberId}")
    public DeveloperDetailDto getDeveloperDetail(
            @PathVariable String memberId
    ) {
        log.info("GET /developers"+memberId+" HTTP/1.1");

        return dmakerService.getDeveloperDetail(memberId);
    }

    @PostMapping("/developers")
    public CreateDeveloperDto.Response createDeveloper(
            @Valid @RequestBody CreateDeveloperDto.Request request
            ) {
        log.info("POST /developers HTTP/1.1 - request : " + request);

        return dmakerService.createDeveloper(request);
    }
}
