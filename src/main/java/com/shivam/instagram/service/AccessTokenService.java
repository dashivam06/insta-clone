package com.shivam.instagram.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.shivam.instagram.dto.IpLocationInfo;
import com.shivam.instagram.entity.AccessToken;
import com.shivam.instagram.jwt.AccessTokenJwtUtil;
import com.shivam.instagram.repository.AccessTokenRepository;
import com.shivam.instagram.utils.Time;
import jakarta.servlet.http.HttpServletRequest;
import nl.basjes.parse.useragent.UserAgent;
import nl.basjes.parse.useragent.UserAgentAnalyzer;
import reactor.core.publisher.Mono;

@Service
public class AccessTokenService {

    @Autowired
    IpLocatorService ipLocatorService ;

    @Autowired
    @Lazy
    AccessTokenJwtUtil accessTokenJwtUtil;

    @Autowired
    AccessTokenRepository accessTokenRepository ;



    public AccessToken saveAccessToken(String userKey) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();

        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }


        Mono<IpLocationInfo> ipInfo = ipLocatorService.getIpLoactionInfo(ip);

        IpLocationInfo ipLocationInfo = ipInfo.block();

        System.out.println(ipLocationInfo);

         // Get the User-Agent
         String userAgentString = request.getHeader("User-Agent");

         // Parse User-Agent for device details (using User-Agent library)
         UserAgentAnalyzer userAgentAnalyzer = UserAgentAnalyzer.newBuilder().build();
         UserAgent userAgent = userAgentAnalyzer.parse(userAgentString);
 
         String deviceName = userAgent.getValue("DeviceName");
        //  String operatingSystem = userAgent.getValue("OperatingSystemName");

        String token = generateToken(userKey);

        AccessToken accessToken = new AccessToken(token, deviceName, ipLocationInfo.getIp(), ipLocationInfo.getCity(), ipLocationInfo.getCountryName(), accessTokenJwtUtil.extractUserId(token),Time.getGMT_Time("yyyy-MM-dd HH:mm:ss"));

        accessTokenRepository.save(accessToken);

        
        return accessToken;

        
    }


    public String generateToken(String userKey) {
        
        String token = accessTokenJwtUtil.generateToken(userKey);
        return token;
    }

    // public IpLocationInfo saveAccessToken(String ip) {

    //     AccessToken accessToken = new AccessToken();

    //     Mono<IpLocationInfo> ipInfo = ipLocatorService.getIpLoactionInfo(ip);

    //     IpLocationInfo ipLocationInfo = ipInfo.block();

    //     System.out.println(ipLocationInfo.toString());

    //     return ipLocationInfo;
    // }
}
