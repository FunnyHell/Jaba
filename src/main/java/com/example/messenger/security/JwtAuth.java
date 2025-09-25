/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.messenger.security;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.messenger.model.UserDetail;
import com.example.messenger.service.UserDetailService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author FunnyHell
 */

@Component
@RequiredArgsConstructor
public class JwtAuth extends OncePerRequestFilter {
    private final JwtToken jwtToken;
    private final UserDetailService userDetailService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, 
                                    @NonNull HttpServletResponse response, 
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenFromRequest(request);

        if (request.getRequestURI().startsWith("/style/") ||
                request.getRequestURI().startsWith("/script/") ||
                request.getRequestURI().equals("/auth")) {
                    filterChain.doFilter(request, response);
                    return;
                }

        if (token == null && request.getRequestURI().startsWith("/api/auth") || 
                request.getRequestURI().startsWith("/api/register")) {
                    filterChain.doFilter(request, response);
                    return;
        }

        Claims claims;
        try {
            claims = jwtToken.getClaimsFromToken(token);

            if (!"access".equals(claims.get("type", String.class))) {
                sendError(response, "Invalid token type", HttpStatus.FORBIDDEN);
                return;
            }
        } catch (JwtException | IllegalArgumentException e) {
            sendError(response, "Invalid token", HttpStatus.UNAUTHORIZED);
            return;
        }

        String username = claims.getSubject();
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetail userDetail = userDetailService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authentication = 
                new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        String tokenParam = request.getParameter("token");
        if (tokenParam != null) {
            return tokenParam;
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("access_token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private void sendError(HttpServletResponse response, String message, HttpStatus status) throws IOException {
        if (response.isCommitted()) {
            return;
        }
        Cookie cookie = new Cookie("access_token", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        response.setContentType("application/json");
        response.setStatus(status.value());
        response.getWriter().write(
            String.format("{\"error\": \"%s\", \"message\": \"%s\"}", status.getReasonPhrase(), message)
        );
    }
}
