package com.gtelant.commerce_service.services;

import com.gtelant.commerce_service.models.User;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.websocket.Decoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.DefaultMessageCodesResolver;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtService {

    //負責 產生/解析 JWT（Json web token）



    private Key getKey() {
        byte [] keyByte = Decoders.BASE64.decode("dGlueXNhbWVoYW5kc29tZXlvdW5nY2FsbHJlY29yZGdpZnRpbnZlbnRlZHdpdGhvdXQ=");
        return Keys.hmacShaKeyFor(keyByte);
    }

    //從JWT中解析出Email
    public String getEmailFromToken(String jwtToken) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody().getSubject();
    }
    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ 28800000)) //八小時後過期
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

}
