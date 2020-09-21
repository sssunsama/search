package com.dev.search.common.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class RestApiClient {

    public static String callByGetApi (String endPoint, String restApiKey) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", restApiKey);

        HttpEntity<String> entity = new HttpEntity<String>(httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(endPoint, HttpMethod.GET, entity, String.class);

        return responseEntity.getBody();
    }
}
