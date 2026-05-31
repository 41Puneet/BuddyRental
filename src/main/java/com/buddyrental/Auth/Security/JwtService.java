package com.buddyrental.Auth.Security;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Date;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    
    @Value("${jwt.secret.key}")
    private String secretKey;


    @Value("${jwt.secret.key.expiration}")
    private long secretKeyExpiration;


    private Key GetSignInKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generateToken(String email){
        return Jwts.builder()
        .subject(email)
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis()+secretKeyExpiration))
        .signWith(GetSignInKey())
        .compact();
    }
}
