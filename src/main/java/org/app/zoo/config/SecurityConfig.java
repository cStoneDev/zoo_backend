package org.app.zoo.config;

import org.app.zoo.auth.repository.Token;
import org.app.zoo.auth.repository.TokenRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final TokenRepository tokenRepository;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter, AuthenticationProvider authenticationProvider, TokenRepository tokenRepository ){
        this.jwtAuthFilter = jwtAuthFilter;
        this.authenticationProvider = authenticationProvider;
        this.tokenRepository = tokenRepository;
    }


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(req -> 
            req.requestMatchers("/auth/**", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**")
                .permitAll()
                .anyRequest()
                .authenticated()
        )
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        .logout(logout -> 
                logout.logoutUrl("/auth/logout")
                    .addLogoutHandler((request, response, authentication)-> {
                        final var authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
                        logout(authHeader);
                    })
                    .logoutSuccessHandler((request, response, authentication) ->
                        SecurityContextHolder.clearContext())                
        );

        return http.build();
    }

    private void logout(final String token){
        if(token == null || !token.startsWith("Bearer")){
            throw new IllegalArgumentException("Invalid token");
        }

        final String jwtToken = token.substring(7);
        final Token foundToken = tokenRepository.findByToken(jwtToken)
            .orElseThrow(() -> new IllegalArgumentException("Invalid token"));
        foundToken.setExpired(true);
        foundToken.setRevoked(true);
        tokenRepository.save(foundToken);
    }
    

    /*@Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // Deshabilita CSRF
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // Permite todas las solicitudes sin restricción
            )
            .httpBasic(httpBasic -> httpBasic.disable()) // Deshabilita autenticación básica
            .formLogin(formLogin -> formLogin.disable()); // Deshabilita el formulario de inicio de sesión

        return http.build();
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests((auth) -> auth.anyRequest().permitAll()).build();
    }*/
}
