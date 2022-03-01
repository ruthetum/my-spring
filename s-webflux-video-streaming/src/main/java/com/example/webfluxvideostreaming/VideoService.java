package com.example.webfluxvideostreaming;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class VideoService {

    private final ResourceLoader resourceLoader;
    private final VideoRepository videoRepository;

    public Mono<Resource> getVideo(Long id) {
        return Mono.fromSupplier(() -> resourceLoader
                .getResource(videoRepository.getResource(id)));
    }
}
