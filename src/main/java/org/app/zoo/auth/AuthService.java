package org.app.zoo.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Collectors;
import java.security.Key;

@Service
public class AuthService{

    private final AuthenticationManager authenticationManager;
    private final String SECRET_KEY_STRING = "mySecretKeyzxxxxxxxxxxxzsdasdasdasdqweqwtrwgetheeqwqwdqefwrgergqerfqwdqwfwegqwfqegfqcasdqwrqwfwrgethytkyiewfwefnubuwfqvyfyuqvfyuqfv"; // esto hay que tirarlo en .env para produccion
    // Create Key object
    Key secretKey = Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes());

    public AuthService(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /*public String authenticate(AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        User user = (User) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("roles", user.getAuthorities().stream()
                        .map(Object::toString).collect(Collectors.toList()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(secretKey, SignatureAlgorithm.HS256) // Use the Key object here
                .compact();
    }*/

    public String authenticate(AuthRequest authRequest) {
        try {
            // Log para depurar
            System.out.println("Autenticando usuario: " + authRequest.getUsername());
    
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
    
            User user = (User) authentication.getPrincipal();
    
            return Jwts.builder()
                    .setSubject(user.getUsername())
                    .claim("roles", user.getAuthorities().stream()
                            .map(Object::toString).collect(Collectors.toList()))
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                    .signWith(secretKey, SignatureAlgorithm.HS256)
                    .compact();
        } catch (BadCredentialsException e) {
            // Log para errores de credenciales incorrectas
            System.err.println("Credenciales incorrectas para el usuario: " + authRequest.getUsername());
            throw new UsernameNotFoundException("Credenciales incorrectas", e);
        }
    }
    

}
