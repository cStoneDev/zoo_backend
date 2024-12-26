package org.app.zoo.species;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Schema(description = "Details about a species")
@Table(name = "especie")
public class Species {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_especie", nullable = false)
    private int id;

    @Column(name = "nombre", nullable = false)
    private String name;

    public Species(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Species(String name) {
        this.name = name;
    }

    public Species() {
    }
    
    public int getId_species() {
        return id;
    }

    public void setId_species(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
