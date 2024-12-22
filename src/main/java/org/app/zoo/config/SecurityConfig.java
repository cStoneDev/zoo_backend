package org.app.zoo.config;

import org.app.zoo.user.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // Deshabilita CSRF
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // Permite todas las solicitudes sin autenticación
            )
            .httpBasic(Customizer.withDefaults()); // Configuración básica de autenticación HTTP (opcional)
    
        return http.build();
    }
    /*@Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // Deshabilita CSRF
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/auth/**").permitAll() // Permite todas las solicitudes en /auth/**
            .anyRequest().authenticated() // Requiere autenticación para otras solicitudes
        )
        .httpBasic(Customizer.withDefaults()); // Configuración básica de autenticación HTTP

        return http.build();
    }*/

    @Bean
    AuthenticationManager authManager(HttpSecurity http, PasswordEncoder passwordEncoder, UserService userService) throws Exception {
        AuthenticationManagerBuilder authManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authManagerBuilder.userDetailsService(userService).passwordEncoder(passwordEncoder);
        return authManagerBuilder.build();
    }
    
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
