package com.shivam.instagram.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import jakarta.servlet.http.HttpServletRequest;
import reactor.core.publisher.Mono;

@Service
public class DeviceInfo {

    @Autowired
    private WebClient webClient;

    String url = "https://theapicompany.com/deviceAPI.js?id=XXX";

    String id = "deviceAPI-88mbws56cg";

    public void getOS(HttpServletRequest httpServletRequest) {

        String userAgent = httpServletRequest.getHeader("USER_AGENT");

        Mono<String> deviceName = webClient.get()
                .uri(url.replace("XXX", id))
                .retrieve()
                .bodyToMono(String.class);

        System.out.println(deviceName);
    }
}
