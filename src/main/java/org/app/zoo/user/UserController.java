package org.app.zoo.user;

import java.util.List;
import java.util.Map;

import org.app.zoo.user.dto.in.UserForgotPasswordDTO;
import org.app.zoo.user.dto.in.UserResetPasswordDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/users")
@Schema(description = "User controller class to handle HTTP requests")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        return userService.findUserById(id)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    
    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, String>> forgotPassword(@RequestBody @Valid UserForgotPasswordDTO userDto) {
        userService.sendForgotPasswordEmail(userDto.email());
        return ResponseEntity.ok().body(Map.of("message", "Se ha enviado un enlace de restablecimiento de contraseña a tu correo electrónico"));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, String>> resetPassword(@RequestBody @Valid UserResetPasswordDTO userDto) {
        userService.resetPassword(userDto.token(), userDto.password());
        return ResponseEntity.ok().body(Map.of("message", "Contraseña actualizada correctamente"));
    }
}

