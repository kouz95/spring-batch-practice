package com.example.batch.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@Getter
@RequiredArgsConstructor
public class NaverResponse {
    private final String title;

    private NaverResponse() {
        this(null);
    }

    @SneakyThrows
    public static NaverResponse of(Map<String, String> item) {
        String broadcastTitle = item.get("broadcastTitle");
        log.info(broadcastTitle);
        return new NaverResponse(broadcastTitle);
    }
}
