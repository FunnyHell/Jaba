/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.messenger.security;

import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.messenger.dto.JwtResponseDto;
import com.example.messenger.model.UserDetail;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
 *
 * @author FunnyHell
 */
@Component
public class JwtToken {
    @Value("$(application.jwt.secret)")
    private String SECRET_KEY;

    @Value("$(application.jwt.access.expire)")
    private long ACCESS_EXPIRE;

    @Value("$(application.jwt.refresh.expire)")
    private long REFRESH_EXPIRE;

    public JwtResponseDto generateToken(UserDetail userDetail) {
        String access = generateAccessToken(userDetail);
        String refresh = generateRefreshToken(userDetail);

        return new JwtResponseDto(access, refresh);
    }

    private String generateRefreshToken(UserDetail userDetail) {
        Date now = new Date();
        return Jwts.builder()
                .subject(userDetail.getUsername())
                .claim("type", "refresh")
                .issuedAt(now)
                .expiration(new Date(now.getTime() + REFRESH_EXPIRE))
                .signWith(getSecretKey())
                .compact();
    }

    private String generateAccessToken(UserDetail userDetail) {
        Date now = new Date();
        return Jwts.builder()
                .subject(userDetail.getUsername())
                .claim("type", "access")
                .issuedAt(now)
                .expiration(new Date(now.getTime() + ACCESS_EXPIRE))
                .signWith(getSecretKey())
                .compact();
    }

    public boolean validateTokenType(String token, UserDetail userDetail) {
        try {
            Jws<Claims> claimsJws = Jwts.parser()
                        .verifyWith(getSecretKey())
                        .build()
                        .parseSignedClaims(token);

            Claims claims = claimsJws.getPayload();
            return claims.getSubject().equals(userDetail.getUsername()) &&
                    claims.getExpiration().after(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private SecretKey getSecretKey() {
        byte[] encodedKey = Base64.getDecoder().decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(encodedKey);
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token).getPayload();
    }
}
