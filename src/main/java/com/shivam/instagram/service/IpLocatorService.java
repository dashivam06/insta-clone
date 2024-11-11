package com.shivam.instagram.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.shivam.instagram.dto.IpLocationInfo;

import reactor.core.publisher.Mono;

@Service
public class IpLocatorService 
{
    
    private final WebClient webClient ;

    public IpLocatorService(@Qualifier("ipLocatorWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<IpLocationInfo> getIpLoactionInfo(String ip)
    {
        return webClient    
                    .get()
                    .uri("/"+ip+"/json/")
                    .retrieve()
                    .bodyToMono(IpLocationInfo.class);
    }



    public Mono<IpLocationInfo> getIpLoactionInfo()
    {
        return webClient    
                    .get()
                    .uri("/json/")
                    .retrieve()
                    .bodyToMono(IpLocationInfo.class);
    }
    
}
