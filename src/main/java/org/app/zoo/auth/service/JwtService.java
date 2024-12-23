package org.app.zoo.auth.service;

import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.app.zoo.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    
    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration}")
    private long jwtExpiration;

    @Value("${security.jwt.refresh-token.expiration}")
    private long refreshExpiration;


    public String extractUsername(final String token) {
        final Claims jwtToken = Jwts.parserBuilder()
                .setSigningKey(getSignInKey()) // Changed to setSigningKey()
                .build()
                .parseClaimsJws(token) // Changed to parseClaimsJws to handle signed tokens
                .getBody();
        return jwtToken.getSubject();
    }

    public Date extractExpiration(final String token) {
        final Claims jwtToken = Jwts.parserBuilder()
                .setSigningKey(getSignInKey()) // Changed to setSigningKey()
                .build()
                .parseClaimsJws(token) // Changed to parseClaimsJws to handle signed tokens
                .getBody();
        return jwtToken.getExpiration();
    }

    public String generateToken(final User user){
        return buildToken(user, jwtExpiration);
    }

    public String generateRefreshToken(final User user){
        return buildToken(user, refreshExpiration);
    }

    private String buildToken(final User user, final long expiration){
        return Jwts.builder() // parametros que le queremos dar al token 
            .setId(user.getId_user().toString()) // id (opcional)
            .setClaims(Map.of("name", user.getUsername())) // info adicional (opcional)
            .setSubject(user.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(getSignInKey())
            .compact();
    }

    public boolean isTokenValid(final String token, final User user){
        final String username = extractUsername(token);
        return (username.equals(user.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(final String token){
        return extractExpiration(token).before(new Date());
    }

    // Genera una llave secreta a partir de secret Key con el algoritmo generado    
    private SecretKey getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
