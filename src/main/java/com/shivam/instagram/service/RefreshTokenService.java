package com.shivam.instagram.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shivam.instagram.entity.RefreshToken;
import com.shivam.instagram.jwt.RefreshTokenJwtUtil;
import com.shivam.instagram.repository.RefreshTokenRepository;
import com.shivam.instagram.utils.Time;

@Service
public class RefreshTokenService 
{

    @Autowired
    RefreshTokenRepository refreshTokenRepository ;

    @Autowired
    RefreshTokenJwtUtil refreshTokenJwtUtil;



    public RefreshToken saveRefreshToken(String userKey)
    {
        String token = refreshTokenJwtUtil.generateToken(userKey);
        RefreshToken refreshToken = new RefreshToken(token, true, refreshTokenJwtUtil.extractUserId(token), Time.getGMT_Time("yyyy-MM-dd HH:mm:ss"), Time.getGMT_Time("yyyy-MM-dd HH:mm:ss"));
        refreshTokenRepository.save(refreshToken);

        return refreshToken;
    }




// public String generateToken(String userKey) {
    
//     String token = refreshTokenJwtUtil.generateToken(userKey);
//     return token;
// }

}
