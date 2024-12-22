package org.app.zoo.user;

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
import jakarta.persistence.Table;


@Entity
@Schema(description = "Details about users")
@Table(name = "usuario")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_usuario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_rol", nullable = false)
    private Role rol;

    @Column(name = "nombre_usuario", nullable = false)
    private String username;
    
    @Column(name = "clave")
    private String password;

    public User(){}

    public User(int id_usuario, Role rol, String username, String password) {
        setId_usuario(id_usuario);
        setRol(rol);
        setUsername(username);
        setPassword(password);
    }


    public int getId_usuario() {
        return id_usuario;
    }
    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }
    public Role getRol() {
        return rol;
    }
    public void setRol(Role rol) {
        this.rol = rol;
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

