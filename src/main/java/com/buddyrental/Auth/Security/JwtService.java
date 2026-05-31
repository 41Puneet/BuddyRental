package com.buddyrental.Auth.Security;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.Date;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import io.jsonwebtoken.security.Keys;
import com.buddyrental.Entity.User;

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
    public String extractEmail(String token){
        return Jwts.parserBuilder()
        .setSigningKey(GetSignInKey())
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
    }
    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractEmail(token);

        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(GetSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }

}
