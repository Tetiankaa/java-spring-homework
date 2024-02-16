package com.example.javaspringhomework.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.signinKey}")
    private String signinKey;
    private Key key;

    @PostConstruct
    public void setUpKey(){
        key = Keys.hmacShaKeyFor(signinKey.getBytes(StandardCharsets.UTF_8));
    }


    public String generateAccessToken(UserDetails userDetails,long tokenTimeDuration){
        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .toList();

        return Jwts
                .builder()
                .setClaims(Map.of("roles",roles))
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + tokenTimeDuration))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }


    public String generateRefreshToken(UserDetails userDetails, long tokenTimeDuration){
            return Jwts.builder()
                    .setClaims(Map.of("type","refresh"))
                    .setSubject(userDetails.getUsername())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + tokenTimeDuration))
                    .signWith(key,SignatureAlgorithm.HS256)
                    .compact();
    }

    public String extractUsername(String token){
        return parseToken(token,Claims::getSubject);
    }

    public Date extractExpiration(String token){
        return parseToken(token,Claims::getExpiration);
    }

    public boolean isRefreshToken(String token){
        return parseToken(token,claims -> Objects.equals(claims.get("type", String.class),"refresh"));
    }

    private <T> T parseToken(String token, Function<Claims, T> resolve){
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return resolve.apply(claims);
    }
}
