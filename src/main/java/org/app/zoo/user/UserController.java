package org.app.zoo.user;


import java.util.Map;

import org.app.zoo.user.dto.in.UserForgotPasswordDTO;
import org.app.zoo.user.dto.in.UserInputDTO;
import org.app.zoo.user.dto.in.UserResetPasswordDTO;
import org.app.zoo.user.dto.out.UserOutputDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    @PreAuthorize("hasRole('Administrador')")
    public ResponseEntity<UserOutputDTO> createUser(@RequestBody UserInputDTO userInputDTO) {
        return new ResponseEntity<>(userService.createUser(userInputDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('Administrador')")
    public ResponseEntity<UserOutputDTO> getUserById(@PathVariable Long id) {
        
        UserOutputDTO userOutputDTO = userService.findUserById(id);
        return ResponseEntity.ok(userOutputDTO);
                
    }

    @GetMapping
    @PreAuthorize("hasRole('Administrador')")
    public Page<UserOutputDTO> getAllUsers(
        @RequestParam(defaultValue = "0") int pageNumber,
        @RequestParam(defaultValue = "10") int pageSize
    ) {
        return userService.getAllUsers(pageNumber,pageSize);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('Administrador')")
    public ResponseEntity<UserOutputDTO> updateUser(@PathVariable Long id, @RequestBody UserInputDTO updatedUser) {
        UserOutputDTO user = userService.updateUser(id, updatedUser);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/search")
    @PreAuthorize("hasRole('Administrador')")
    public ResponseEntity<Page<UserOutputDTO>> searchUsers(@RequestBody UserSearchCriteria userSearchCriteria) {
        
        return new ResponseEntity<>(userService.searchUsers(userSearchCriteria) , HttpStatus.OK);
        
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('Administrador')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 indica que la operación fue exitosa y no hay contenido en la respuesta
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

