package org.app.zoo.breed;

import org.app.zoo.specie.Specie;

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
@Schema(description = "Details about a breed")
@Table(name = "raza")
public class Breed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_raza", nullable = false)
    private int id_breed;

    @Column(name = "nombre", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_especie", nullable = false)
    private Specie specie;

    public Breed() {
    }

    public Breed(int id_breed, String name, Specie specie) {
        this.id_breed = id_breed;
        this.name = name;
        this.specie = specie;
    }

    public int getId_breed() {
        return id_breed;
    }

    public void setId_breed(int id_breed) {
        this.id_breed = id_breed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Specie getSpecie() {
        return specie;
    }

    public void setSpecie(Specie specie) {
        this.specie = specie;
    }
}
