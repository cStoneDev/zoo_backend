package org.app.zoo.role;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Schema(description = "Details about roles")
@Table(name = "rol")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol", nullable = false)
    private int id_role;

    @Column(name = "nombre", nullable = false, unique = true)
    private String name;

    

    public Role(){}

    public Role(int id_role, String name) {
        setId_role(id_role);
        setName(name);
    }

    public int getId_role() {
        return id_role;
    }
    public void setId_role(int id_role) {
        this.id_role = id_role;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    

    
}