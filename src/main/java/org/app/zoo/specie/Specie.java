package org.app.zoo.specie;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Schema(description = "Details about a specie")
@Table(name = "especie")
public class Specie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_especie", nullable = false)
    private int id_specie;

    @Column(name = "nombre", nullable = false)
    private String name;

    public Specie(int id_specie, String name) {
        this.id_specie = id_specie;
        this.name = name;
    }

    public Specie() {
    }
    
    public int getId_specie() {
        return id_specie;
    }

    public void setId_specie(int id_specie) {
        this.id_specie = id_specie;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
    
    
}
