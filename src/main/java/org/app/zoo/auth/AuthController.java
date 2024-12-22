package org.app.zoo.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        // Verifica que authRequest.getUsername() no sea null o "NONE_PROVIDED"
        System.out.println("username recibido: " + authRequest.getUsername());
        String token = authService.authenticate(authRequest);
        return ResponseEntity.ok(new AuthResponse(token));
    }
    /*@PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
    System.out.println("username recibido: " + username);
    System.out.println("password recibido: " + password);
    
    AuthRequest authRequest = new AuthRequest();
    authRequest.setUsername(username);
    authRequest.setPassword(password);
    
    String token = authService.authenticate(authRequest);
    return ResponseEntity.ok(new AuthResponse(token));
}*/

}
