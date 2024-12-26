package org.app.zoo.breed;

import org.app.zoo.species.Species;

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
    private int id;

    @Column(name = "nombre", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_especie", nullable = false)
    private Species species;

    public Breed() {
    }

    public Breed(int id, String name, Species species) {
        this.id = id;
        this.name = name;
        this.species = species;
    }

    public Breed(String name, Species species) {
        this.name = name;
        this.species = species;
    }

    public int getId_breed() {
        return id;
    }

    public void setId_breed(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Species getSpecies() {
        return species;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }
}
