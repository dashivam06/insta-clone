package com.shivam.instagram.utils;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.shivam.instagram.entity.AccessToken;
import com.shivam.instagram.entity.RefreshToken;
import com.shivam.instagram.jwt.RefreshTokenJwtUtil;
import com.shivam.instagram.service.AccessTokenService;
import com.shivam.instagram.service.RefreshTokenService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class CookieHandler 
{

   


    @Value("${myapp.instagram.cookie.access-token.expiration-time}")
    Integer accessTokenCookieExpTimeInSec ;

    @Value("${myapp.instagram.cookie.refresh-token.expiration-time}")
    Integer refreshTokenCookieExpTimeInSec ;

    @Autowired
    @Lazy
    AccessTokenService accessTokenService ;

    @Autowired
    RefreshTokenJwtUtil refreshTokenJwtUtil;

    @Autowired
    RefreshTokenService refreshTokenService ;


    public Optional<Cookie> getDesiredCookie(HttpServletRequest httpServletRequest , String cookieName)
    {
        Cookie[] cookie = httpServletRequest.getCookies();

        if(cookie != null)
        {
            Optional<Cookie> auth_token_cookie =  Arrays.stream(cookie).filter(each -> each.getName().equals(cookieName)).findAny();
            return auth_token_cookie;
        }

        return Optional.empty();
    }

    public Cookie createCookie(HttpServletResponse httpServletResponse, String name , String value, String domain, String path, Integer durationInMilliSeconds,boolean httpOnly, boolean secure, String sameSite, String priority)
    {
        Cookie cookie = new Cookie(name,value);
        cookie.setDomain(domain);
        cookie.setHttpOnly(httpOnly);
        cookie.setPath(path);
        cookie.setMaxAge(durationInMilliSeconds);
        cookie.setSecure(secure);
        cookie.setAttribute("Priority", priority);
        cookie.setAttribute("SameSite", sameSite);

        return cookie;

    }


    public void setDesiredCookie(HttpServletResponse httpServletResponse, String name , String value, String domain, String path, Integer durationInMilliSeconds,boolean httpOnly, boolean secure, String sameSite, String priority)
    {
        Cookie cookie = createCookie(httpServletResponse, name, value, domain, path, durationInMilliSeconds, httpOnly, secure, sameSite, priority);


        httpServletResponse.addCookie(cookie);
    }


    public void setDesiredCookie(HttpServletResponse httpServletResponse, String name , String value, String domain,String path , Integer durationInMilliSeconds, String sameSite)
    {
       
        setDesiredCookie(httpServletResponse, name, value, domain,path, durationInMilliSeconds, true, true, sameSite,"");

    }
   

   
    public void setDesiredCookie(HttpServletResponse httpServletResponse, String name , String value,Integer durationInMilliSeconds)
    {
       
        setDesiredCookie(httpServletResponse, name, value, "shivamthakur.com.np","/", durationInMilliSeconds, true, true, "None","");

    }




    public void setAccessTokenInCookie(HttpServletResponse httpServletResponse, String userKey, String domain,
            String path, String sameSite) 
    {

        AccessToken accessJwtToken = accessTokenService.saveAccessToken(userKey);

        setDesiredCookie(httpServletResponse, "access_token", accessJwtToken.getToken(), domain, path,
                accessTokenCookieExpTimeInSec, sameSite);
    }





    public void setRefreshTokenInCookie(HttpServletResponse httpServletResponse, String userKey, String domain,
            String path,  String sameSite) {

        RefreshToken refreshJwtToken = refreshTokenService.saveRefreshToken(userKey);

        setDesiredCookie(httpServletResponse, "refresh_token", refreshJwtToken.getToken(), null, "/",
        refreshTokenCookieExpTimeInSec,"None");

    }


    
    
}
