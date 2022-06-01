package com.example.propertyrental.security;


import com.example.propertyrental.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {


    @Value("${property-rental.security.jwt.secret}")
    private String secret;
    @Value("${property-rental.security.jwt.time}")
    private String time;

    public String genarateToken(User user) {
        return doGenarateToken(user.getUserName());
    }

    private String doGenarateToken(String userName) {
        return Jwts.builder()
                .setClaims(Jwts.claims().setSubject(userName))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + Integer.parseInt(time) * 1000))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public String getUserNameFromToken(String token) {
        return getAllClaims(token).getSubject();
    }

    private Claims getAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }


    public Boolean validateToken(String token) {
        try {
            getAllClaims(token);
            return true;
        } catch (JwtException e) {
            throw new JwtException(e.getMessage());
        }
    }

}
