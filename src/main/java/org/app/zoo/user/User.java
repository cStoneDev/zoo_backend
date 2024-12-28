package org.app.zoo.user;

import java.util.List;

import org.app.zoo.role.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


import org.app.zoo.auth.repository.Token;

@Entity
@Schema(description = "Details about users")
@Table(name = "usuario")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario", nullable = false)
    private Long id;

    @Column(name = "correo_electronico")
    private String email;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_rol", nullable = false)
    private Role role;

    @Column(name = "nombre_usuario", nullable = false)
    private String username;

    @Column(name = "clave")
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Token> tokens;

    private String resetToken;

    public User(){}

    public User(Long id_user, Role role, String username, String password) {
        setId_user(id_user);
        setRole(role);
        setUsername(username);
        setPassword(password);
    }

    public User( Role role, String username, String password) {
        setRole(role);
        setUsername(username);
        setPassword(password);
    }

    public Long getId_user() {
        return id;
    }
    public void setId_user(Long id_user) {
        this.id = id_user;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}

