package org.app.zoo.user;

import java.util.List;
import java.util.Optional;

import org.app.zoo.role.Role;
import org.app.zoo.role.RoleRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import io.swagger.v3.oas.annotations.media.Schema;

@Service
@Schema(description = "User service who has the implementations of crud functions and more")
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User createUser(User user) {
        // Validación de existencia del rol
        Role role = roleRepository.findByNombre(user.getRol().getNombre())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        
        user.setRol(role);
        user.setClave(bCryptPasswordEncoder.encode(user.getClave()));
        return userRepository.save(user);
    }

    public Optional<User> findUserById(Integer id) {
        return userRepository.findById(id);
    }

    public Optional<User> findUserByNombreUsuario(String nombreUsuario) {
        return userRepository.findByNombreUsuario(nombreUsuario);
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
