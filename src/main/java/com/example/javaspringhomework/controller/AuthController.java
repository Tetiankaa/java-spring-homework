package com.example.javaspringhomework.controller;

import com.example.javaspringhomework.dto.JwtRequest;
import com.example.javaspringhomework.dto.JwtRequestRefresh;
import com.example.javaspringhomework.dto.JwtResponse;
import com.example.javaspringhomework.exception.JwtAuthException;
import com.example.javaspringhomework.service.JwtService;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    private static long ACCESS_TOKEN_TTL_SECONDS = 30000;
    private static long REFRESH_TOKEN_TTL_SECONDS = 90000;


    @PostMapping("/api/auth/signin")
    public ResponseEntity<JwtResponse> signin(@RequestBody JwtRequest request){

        UsernamePasswordAuthenticationToken unauthenticated = UsernamePasswordAuthenticationToken.unauthenticated(request.getUsername(), request.getPassword());
        authenticationManager.authenticate(unauthenticated);

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());

        String accessToken = jwtService.generateAccessToken(userDetails, ACCESS_TOKEN_TTL_SECONDS);
        String refreshToken = jwtService.generateRefreshToken(userDetails, REFRESH_TOKEN_TTL_SECONDS);

        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setAccessToken(accessToken);
        jwtResponse.setRefreshToken(refreshToken);

        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/api/auth/refresh")
    public ResponseEntity<JwtResponse> refresh(@RequestBody JwtRequestRefresh requestRefreshToken){
        try{
            Date expirationRefresh = jwtService.extractExpiration(requestRefreshToken.getRefreshToken());

            if (expirationRefresh.before(new Date())){
                throw new JwtAuthException("Refresh token has expired");
            }

            String username = jwtService.extractUsername(requestRefreshToken.getRefreshToken());
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            String accessToken = jwtService.generateAccessToken(userDetails, ACCESS_TOKEN_TTL_SECONDS);
            Date expirationAccess = jwtService.extractExpiration(accessToken);

            JwtResponse jwtResponse = new JwtResponse();
            jwtResponse.setAccessToken(accessToken);

            if (expirationRefresh.before(expirationAccess)){
                String refreshToken = jwtService.generateRefreshToken(userDetails, REFRESH_TOKEN_TTL_SECONDS);
                jwtResponse.setRefreshToken(refreshToken);
            }else {
                jwtResponse.setRefreshToken(requestRefreshToken.getRefreshToken());
            }

            return ResponseEntity.ok(jwtResponse);

        }catch (JwtException exception){
            throw new JwtAuthException(exception.getMessage(),exception);
        }
    }
}
