package com.spring.filters;

import com.spring.constants.SecurityConstant;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class JWTTokenFilter extends OncePerRequestFilter {

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/user");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        if(authentication != null){
            SecretKey secretKey = Keys.hmacShaKeyFor(SecurityConstant.KEY.getBytes(StandardCharsets.UTF_8));
            String jwt = Jwts.builder().setSubject("JWT Token")
                    .claim("email",authentication.getName())
                    .claim("authorities",authorities(authentication.getAuthorities()))
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(new Date().getTime() + 30000)) //ms
                    .signWith(secretKey).compact();

            response.setHeader(SecurityConstant.HEADER,jwt);
        }
        filterChain.doFilter(request,response);
    }

    private String authorities(Collection<? extends GrantedAuthority> collections){
        Set<String> auth = new HashSet<>();
        for(GrantedAuthority authority : collections){
            auth.add(authority.getAuthority());
        }
        return String.join(",",auth); //"USER,ADMIN" as String
    }
}
