package org.app.zoo.service;

import java.util.List;
import java.util.Optional;

import org.app.zoo.model.Role;
import org.app.zoo.model.User;
import org.app.zoo.repository.RoleRepository;
import org.app.zoo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.swagger.annotations.ApiModel;

@Service
@ApiModel(description = "User service who has the implementations of crud functions and more")
public class UserService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public User createUser(User user) {
        // Validación de existencia del rol
        Role role = roleRepository.findByNombre(user.getRol().getNombre())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        
        user.setRol(role);
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
