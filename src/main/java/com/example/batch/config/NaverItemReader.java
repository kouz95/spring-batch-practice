package com.example.batch.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class NaverItemReader implements ItemReader<List<NaverResponse>> {
    private final ObjectMapper objectMapper;

    @Override
    public List<NaverResponse> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        // https://store.kakao.com/a/home/liveTab/schedules
        String json = WebClient.create().get()
                .uri("https://apis.naver.com/selectiveweb/selectiveweb/v1/home/live-now")
                .retrieve()
                .bodyToMono(String.class)
                .block();
        try {

            List<Map<String, String>> list = (List<Map<String, String>>) objectMapper.readValue(json, Map.class).get("list");
            return list.stream()
                    .map(NaverResponse::of)
                    .collect(Collectors.toList());
        } catch (JsonProcessingException e) {
            throw new RuntimeException();
        }
    }
}
