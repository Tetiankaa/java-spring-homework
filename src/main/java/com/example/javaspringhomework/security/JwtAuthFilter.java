package com.example.javaspringhomework.security;

import com.example.javaspringhomework.exception.JwtAuthException;
import com.example.javaspringhomework.handler.AuthErrorHandler;
import com.example.javaspringhomework.service.JwtService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final AuthErrorHandler authErrorHandler;

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHORIZATION_HEADER_PREFIX = "Bearer ";


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(AUTHORIZATION_HEADER);

        if (!StringUtils.hasText(header) && !StringUtils.startsWithIgnoreCase(header, AUTHORIZATION_HEADER_PREFIX)){
            filterChain.doFilter(request,response);
            return;
        }

        String token = header.substring(AUTHORIZATION_HEADER_PREFIX.length());

        try {
            if (jwtService.extractExpiration(token).before(new Date())){
                filterChain.doFilter(request,response);
                return;
            }
            if (jwtService.isRefreshToken(token)){
                throw new  JwtException("Refresh token can not be used for accessing resources");
            }

            String username = jwtService.extractUsername(token);

            if (StringUtils.hasText(username)){
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authenticated = UsernamePasswordAuthenticationToken.authenticated(username, userDetails.getPassword(), userDetails.getAuthorities());
                WebAuthenticationDetails webAuthenticationDetails = new WebAuthenticationDetailsSource().buildDetails(request);
                authenticated.setDetails(webAuthenticationDetails);

                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                securityContext.setAuthentication(authenticated);

                SecurityContextHolder.setContext(securityContext);
            }

        }catch (JwtException exception){
                authErrorHandler.commence(request,response,new JwtAuthException(exception.getMessage(),exception));
        }
        filterChain.doFilter(request,response);
    }
}
