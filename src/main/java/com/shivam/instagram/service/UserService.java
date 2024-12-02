package com.shivam.instagram.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shivam.instagram.controller.UserWrapper;
import com.shivam.instagram.dto.ResponseBody;
import com.shivam.instagram.entity.User;
import com.shivam.instagram.jwt.AccessTokenJwtUtil;
import com.shivam.instagram.jwt.RefreshTokenJwtUtil;
import com.shivam.instagram.repository.UserRepository;
import com.shivam.instagram.utils.CookieHandler;
import com.shivam.instagram.utils.Time;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    @Lazy
    CookieHandler cookieHandler;

    public ResponseBody saveUser(UserWrapper userWrapper, HttpServletResponse httpServletResponse) {

        Optional<User> optionalUser = userRepository.findByUserName(userWrapper.getUserName());

        if (optionalUser.isEmpty()) {
            User user = new User(userWrapper.getUserName(), userWrapper.getFullName(), userWrapper.getEmail(),
                    passwordEncoder.encode(userWrapper.getPassword()), userWrapper.getProfilePic(),
                    userWrapper.getIsEmailVerified(), userWrapper.getDateOfBirth(),
                    Time.getGMT_Time("yyyy-MM-dd HH:mm:ss"));

            userRepository.save(user);

            cookieHandler.setAccessTokenInCookie(httpServletResponse, userWrapper.getUserName(), null, "/",
                    "None");
            cookieHandler.setRefreshTokenInCookie(httpServletResponse, userWrapper.getUserName(), null, "/",
                    "None");

            return new ResponseBody(true, 200, user, "User successfully created.", "/sign-up");
        }

        return new ResponseBody(true, 409, null, "User with the specified username already exists.", "/sign-up");
        
    }

    public ResponseBody authenticate(HttpServletResponse httpServletResponse, String userKey, String password) {

        ResponseBody responseBody = new ResponseBody();
        responseBody.setSource("/login");

        try {

            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(userKey, password));

            if (authentication.isAuthenticated()) {

                SecurityContextHolder.getContext().setAuthentication(authentication);

                // cookieHandler.setAccessTokenInCookie(httpServletResponse, userKey, null, "/",
                // "None");
                // cookieHandler.setRefreshTokenInCookie(httpServletResponse, userKey, null,
                // "/",
                // "None");

                return new ResponseBody(true, 200, authentication.getPrincipal(), "Login successful", "/login");
            }

        } catch (UsernameNotFoundException | BadCredentialsException e) {
            responseBody.setSuccess(false);
            responseBody.setMessage("Invalid password");
            responseBody.setStatus(401);
        } catch (Exception e) {
            System.out.println("Exception at : UserService.class " + "\nException  :  " + e);
            responseBody.setSuccess(false);
            responseBody.setMessage("Login failed");
            responseBody.setStatus(500);
        }

        return responseBody;

    }

    public Optional<User> findByUsernameOrEmail(String userKey) {

        return userRepository.findByUsernameOrEmail(userKey);
    }

}
