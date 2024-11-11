package com.shivam.instagram.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig 
{
    private WebClient.Builder builder ;

    public WebClientConfig(WebClient.Builder builder) {
        this.builder = builder;
    }

    @Bean("ipLocatorWebClient")
    public WebClient ipLocatorWebClient()
    {
        return builder.baseUrl("https://ipapi.co").build();
    }



}
