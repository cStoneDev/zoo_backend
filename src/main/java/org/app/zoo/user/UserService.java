package org.app.zoo.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.app.zoo.role.Role;
import org.app.zoo.role.RoleRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.swagger.v3.oas.annotations.media.Schema;

@Service
@Schema(description = "User service who has the implementations of crud functions and more")
public class UserService implements UserDetailsService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(User user) {
        // Validación de existencia del rol
        Role role = roleRepository.findByName(user.getRole().getName())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> findUserById(Integer id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    System.out.println("Buscando usuario: " + username); // Verifica si el nombre de usuario es correcto
    
    return userRepository.findByUsername(username)
        .map(user -> {
            return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword()) // Contraseña encriptada
                .authorities(new ArrayList<>()) // Asigna roles si es necesario
                .build();
        })
        .orElseThrow(() -> {
            System.err.println("Usuario no encontrado: " + username); // Log si no se encuentra el usuario
            return new UsernameNotFoundException("Usuario no encontrado: " + username);
        });
}

}