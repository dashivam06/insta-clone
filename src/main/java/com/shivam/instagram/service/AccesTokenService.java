package com.shivam.instagram.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.shivam.instagram.dto.IpLocationInfo;
import com.shivam.instagram.entity.AccessToken;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import reactor.core.publisher.Mono;

@Service
public class AccesTokenService {

    @Autowired
    IpLocatorService ipLocatorService ;

    public IpLocationInfo saveAccessToken() {

        AccessToken accessToken = new AccessToken();

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();

        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        Mono<IpLocationInfo> ipInfo = ipLocatorService.getIpLoactionInfo(ip);

        IpLocationInfo ipLocationInfo = ipInfo.block();

        System.out.println(ipLocationInfo.toString());

        return ipLocationInfo;
    }

    public IpLocationInfo saveAccessToken(String ip) {

        AccessToken accessToken = new AccessToken();

        Mono<IpLocationInfo> ipInfo = ipLocatorService.getIpLoactionInfo(ip);

        IpLocationInfo ipLocationInfo = ipInfo.block();

        System.out.println(ipLocationInfo.toString());

        return ipLocationInfo;
    }
}
