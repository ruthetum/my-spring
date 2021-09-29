package org.example.dmaker.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dmaker.dto.*;
import org.example.dmaker.exception.DmakerException;
import org.example.dmaker.service.DmakerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DmakerController {
    private final DmakerService dmakerService;

    @GetMapping("/developers")
    public List<DeveloperDto> getAllDevelopers() {
        log.info("GET /developers HTTP/1.1");

        return dmakerService.getAllEmployedDevelopers();
    }

    @GetMapping("/developers/{memberId}")
    public DeveloperDetailDto getDeveloperDetail(
            @PathVariable String memberId
    ) {
        log.info("GET /developers/"+memberId+" HTTP/1.1");

        return dmakerService.getDeveloperDetail(memberId);
    }

    @PostMapping("/developers")
    public CreateDeveloperDto.Response createDeveloper(
            @Valid @RequestBody CreateDeveloperDto.Request request
            ) {
        log.info("POST /developers HTTP/1.1 - request : " + request);

        return dmakerService.createDeveloper(request);
    }

    @PutMapping("/developers/{memberId}")
    public DeveloperDetailDto editDeveloperDetail(
            @PathVariable String memberId,
            @Valid @RequestBody EditDeveloperDto.Request request
    ) {
        log.info("PUT /developers/"+memberId+" HTTP/1.1");

        return dmakerService.editDeveloper(memberId, request);
    }

    @DeleteMapping("/developers/{memberId}")
    public DeveloperDetailDto deleteDeveloper(
            @PathVariable String memberId
    ) {
        log.info("DELETE /developers/"+memberId+" HTTP/1.1");

        return dmakerService.deleteDeveloper(memberId);
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler(DmakerException.class)
    public DmakerErrorResponse handleException(
            DmakerException e,
            HttpServletRequest request
    ) {
        log.error("errorCode : {}, url : {}, message : {}",
                e.getDmakerErrorCode(), request.getRequestURI(), e.getDetailMessage());

        return DmakerErrorResponse.builder()
                .errorCode((e.getDmakerErrorCode()))
                .errorMessage(e.getDetailMessage())
                .build();
    }
}
