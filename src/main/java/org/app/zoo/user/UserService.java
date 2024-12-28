package org.app.zoo.user;

import java.util.ArrayList;
import java.util.Optional;

import org.app.zoo.auth.service.JwtService;
import org.app.zoo.config.GlobalExceptionHandler;
import org.app.zoo.config.errorHandling.ConstraintViolationException;
import org.app.zoo.config.errorHandling.InvalidInputException;
import org.app.zoo.config.errorHandling.ResourceAlreadyExistsException;
import org.app.zoo.config.errorHandling.ResourceNotFoundException;
import org.app.zoo.email.service.EmailService;
import org.app.zoo.role.Role;
import org.app.zoo.role.RoleRepository;
import org.app.zoo.user.dto.in.UserInputDTO;
import org.app.zoo.user.dto.out.UserOutputDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.swagger.v3.oas.annotations.media.Schema;

@Service
@Schema(description = "User service who has the implementations of crud functions and more")
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final GlobalExceptionHandler globalExceptionHandler;

    @Value("${mail.baseUrl}")
    private String baseUrl;

    public UserService(GlobalExceptionHandler globalExceptionHandler, UserRepository userRepository, EmailService emailService, RoleRepository roleRepository,
            PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.globalExceptionHandler = globalExceptionHandler;
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    private void validate(UserInputDTO userInputDTO){
        if (userInputDTO.email() == null || userInputDTO.email().trim().isEmpty()) {
            throw new InvalidInputException("El email es inválido o vacío");
        }
        if (userInputDTO.username() == null || userInputDTO.username().length() < 3 || userInputDTO.username().isEmpty()) {
            throw new InvalidInputException("El nombre del proveedor debe tener al menos 3 caracteres y no estar vacío");
        }
    }

    public UserOutputDTO createUser(UserInputDTO userInputDTO) {
        Role role = roleRepository.findById(userInputDTO.roleId())
                .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado"));

        validate(userInputDTO);
        if (userInputDTO.password() == null || userInputDTO.password().trim().isEmpty()) {
            throw new InvalidInputException("La contraseña está vacía");
        }
        User user = new User();
        user.setUsername(userInputDTO.username());
        user.setEmail(userInputDTO.email());
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(userInputDTO.password()));
        
        
        try {
            User userOut = userRepository.save(user);
            return mapToOutputDTO(userOut);
        } catch (Exception e) {
            throw new InvalidInputException(globalExceptionHandler.extractErrorMessage(e.getMessage()));
        }
        
    }

    public UserOutputDTO findUserById(Integer id) {
        return mapToOutputDTO(userRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"))) ;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public UserOutputDTO updateUser(int id, UserInputDTO userInputDTO){
        User user = userRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        validate(userInputDTO);
        boolean exists = userRepository.existsByUsernameAndEmailAndRoleId(
            userInputDTO.username(), 
            userInputDTO.email(), 
            userInputDTO.roleId()
        );

        if(exists){
            throw new ResourceAlreadyExistsException("Ya existe un usuario con las mismas característictas");
        }

        Role role = roleRepository.findById(userInputDTO.roleId())
                .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado"));

        user.setUsername(userInputDTO.username());
        user.setEmail(userInputDTO.email());
        user.setRole(role);

        try {
            User userOut = userRepository.save(user);
            return mapToOutputDTO(userOut);
        } catch (Exception e) {
            throw new InvalidInputException(globalExceptionHandler.extractErrorMessage(e.getMessage()));
        }
        
    }

    public Page<UserOutputDTO> searchUsers(UserSearchCriteria criteria) {

        Specification<User> spec = Specification.where(null);

        // Aplicar cada filtro si es válido
        if (criteria.searchField() != null && !criteria.searchField().isEmpty()){
            spec = spec.and(UserSpecification.filterBySearchField(criteria.searchField()));
        }
        if (criteria.roleId() > 0) {
            spec = spec.and(UserSpecification.filterByRole(criteria.roleId()));
        }

        // Crear un objeto Pageable usando pageNumber y itemsPerPage
        Pageable pageable = PageRequest.of(criteria.pageNumber(), criteria.itemsPerPage(), Sort.by("id"));
        
        // Obtener la lista de animales según la especificación y la paginación
        Page<User> usersPage = userRepository.findAll(spec, pageable);
        
        // Convertir a DTOs
        return usersPage.map(this::mapToOutputDTO);
    }

    public Page<UserOutputDTO> getAllUsers(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("id")); // Crear un objeto Pageable
    
        
        Page<User> userPage = userRepository.findAll(pageable);

        return userPage.map(this::mapToOutputDTO);
    }

    public void deleteUser(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        try {
            userRepository.delete(user);
        } catch (DataIntegrityViolationException e) {
            throw new ConstraintViolationException("No se puede eliminar el usuario porque tiene dependencias relacionadas.");
        }
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

    public void sendForgotPasswordEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        String token = jwtService.generateResetPasswordToken(user);

        String resetLink = baseUrl + "/resetPassword?token=" + token;
        emailService.sendEmail(user.getEmail(), "Restablecimiento de contraseña",
                "Para restablecer tu contraseña, haz clic en el siguiente enlace: " + resetLink);
    }

    public void resetPassword(String token, String newPassword) {
        if (!jwtService.isResetPasswordToken(token)) {
            throw new IllegalArgumentException("Token inválido");
        }
        String username = jwtService.extractUsername(token);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Token inválido"));
    
        if (!jwtService.isTokenValid(token, user)) {
            throw new IllegalArgumentException("Token inválido");
        }
    
        user.setPassword(passwordEncoder.encode(newPassword));
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new InvalidInputException(globalExceptionHandler.extractErrorMessage(e.getMessage()));
        }
        
    }

    private UserOutputDTO mapToOutputDTO(User user){
        UserOutputDTO userOutputDTO = new UserOutputDTO(
            user.getId_user(),
            user.getUsername(),
            user.getEmail(),
            user.getRole().getName()
        );
        return userOutputDTO;
    }

}