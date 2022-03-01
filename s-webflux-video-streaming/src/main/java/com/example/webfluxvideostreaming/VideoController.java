package com.example.webfluxvideostreaming;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @GetMapping(value = "/video/{id}", produces = "video/mp4")
    public Mono<Resource> getVideos(
            @PathVariable("id") Long id,
            @RequestHeader("Range") String range
    ) {
        log.info("range in bytes() : {}", range);
        return videoService.getVideo(id);
    }
}
