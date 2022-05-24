package com.example.propertyrental.security;


import com.example.propertyrental.model.User;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {


    @Value("${jwt.secret}")
    private String secret;

    public String genarateToken(User user){
        return doGanarateToken(user.getUserName());
    }

    private String doGanarateToken(String userName) {
        return Jwts.builder()
                .setClaims(Jwts.claims().setSubject(userName))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 60*60*1000))
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();
    }

    public String getUserNameFromToken(String token){
        return getAllClaims(token).getSubject();
    }

    private Claims getAllClaims(String token){
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }
    private Boolean isTokenExpierd(String token){
        Date expiration = getAllClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        return (getUserNameFromToken(token).equals(userDetails.getUsername()) && !isTokenExpierd(token));
    }


}
