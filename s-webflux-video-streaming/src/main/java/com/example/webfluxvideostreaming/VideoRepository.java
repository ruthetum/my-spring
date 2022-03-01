package com.example.webfluxvideostreaming;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class VideoRepository {

    private Map<Long, String> videos;

    private static final String FORMAT = "classpath:videos/%s.mp4";

    @PostConstruct
    private void init() {
        videos = new HashMap<>();
        videos.put(1L, "test");
    }

    public String getResource(Long id) {
        return String.format(FORMAT, videos.get(id));
    }
}
