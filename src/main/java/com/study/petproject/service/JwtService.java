package com.study.petproject.service;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String jwtSecret;

    public String generateToken(String email) {
        Date date = Date.from(LocalDate.now().plusDays(30).atStartOfDay(ZoneId.systemDefault()).toInstant());
        HashMap<String, Object> addInfo = new HashMap<>();
        addInfo.put("tokenId", UUID.randomUUID());

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(date)
                .addClaims(addInfo)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean validateToken(String jwt) {
        return true;
    }

    public String getUserEmailFromJWT(String jwt) {
        System.out.println(jwt);
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwt).getBody().getSubject();
    }
}
