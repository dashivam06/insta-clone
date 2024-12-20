package com.shivam.instagram.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.shivam.instagram.entity.User;
import com.shivam.instagram.repository.UserRepository;
import com.shivam.instagram.utils.Time;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Component
public class AccessTokenJwtUtil {


    @Value("${myapp.instagram.jwt.access-token.secretkey}")
    String SECRETKEY;


    @Value("${myapp.instagram.jwt.access-token.expiration-time}")
    Integer expirationTime;

    @Autowired
    UserRepository userRepository;

    public String generateToken(String userKey) {

        Optional<User> optionalUser = userRepository.findByUsernameOrEmail(userKey);

        HashMap<String, String> userClaims = new HashMap<>();

        optionalUser.ifPresent((user) -> {
            userClaims.put("user_id", String.valueOf(user.getUserId()));
            userClaims.put("fullname", user.getFullName());
            userClaims.put("email", user.getEmail());
        });

        String token = Jwts.builder()
                .claims(userClaims)
                .subject(userKey)
                .issuedAt(new Date(Time.getGmtDateInMilliSec()))
                .expiration(new Date(Time.getGmtDateInMilliSec() + expirationTime))
                .signWith(getSigningKey())
                .compact();

        return token;
    }

    public SecretKey getSigningKey() {
        byte[] secretkeyInBytes = SECRETKEY.getBytes();
        SecretKey secretKey = Keys.hmacShaKeyFor(secretkeyInBytes);

        return secretKey;
    }

    public Claims extractAllClaims(String token) {

        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("JWT cannot be null or empty");
        }

        try {

            Claims claims = Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();

            return claims;

        } catch (MalformedJwtException e) {
            throw new IllegalArgumentException("Malformed JWT token", e);
        } catch (ExpiredJwtException e) {
            throw new IllegalArgumentException("Expired JWT token", e);
        } catch (SignatureException e) {
            throw new IllegalArgumentException("Invalid JWT signature", e);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid JWT token", e);
        }

    }

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {

        try {
            final Claims claims = extractAllClaims(token);
            return claimResolver.apply(claims);

        } catch (Exception exception) {
            throw new IllegalArgumentException(exception.getMessage());
        }

    }

    public String extractUsername(String token) {

        try {
            String username = extractClaim(token, Claims::getSubject);

            return username;

        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }

    }

    public String extractEmail(String token) {

        try {
            String email = extractClaim(token, claim -> claim.get("email", String.class));

            return email;

        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }

    }


    public Integer extractUserId(String token) {

        try {
            String userIdInString = extractClaim(token, claim -> claim.get("user_id", String.class));

            Integer userId = Integer.parseInt(userIdInString);

            return userId;

        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }

    }

    public boolean isTokenExpired(String token) throws ExpiredJwtException {

        try {
            Date tokenExpTime = extractClaim(token, Claims::getExpiration);
            return tokenExpTime.before(new Date());

        } catch (Exception e) {
            if (e.getMessage().equals("Expired JWT token")) {
                return true;
            } else {
                throw new IllegalArgumentException(e);
            }

        }

    }

    public boolean validateToken(String token, UserDetails userDetails) {

        try {
            final String userName = extractUsername(token);
            return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }

    }

}
