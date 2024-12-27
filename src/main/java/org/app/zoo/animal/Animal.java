package org.app.zoo.animal;

import java.sql.Date;

import org.app.zoo.breed.Breed;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;

@Entity
@Schema(description = "Details about an animal")
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_animal", nullable = false)
    private int id;

    @Size(min=3) //Name cannot be less than 3 characters
    @Column(name = "nombre", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_raza", nullable = false)
    private Breed breed;

    @Column(name = "edad", nullable = false)
    private int age;

    @Column(name = "peso", nullable = false)
    private double weight;

    @Column(name = "fecha_ingreso", nullable = false)
    private Date entryDate;

    protected Animal(){}

    public Animal(int id_animal, String name, Breed breed, int age, double weight, Date entry_date) {
        this.id = id_animal;
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.weight = weight;
        this.entryDate = entry_date;
    }


    public int getId_animal() {
        return id;
    }


    public void setId_animal(int id_animal) {
        this.id = id_animal;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public Breed getBreed() {
        return breed;
    }


    public void setBreed(Breed breed) {
        this.breed = breed;
    }


    public int getAge() {
        return age;
    }


    public void setAge(int age) {
        this.age = age;
    }


    public double getWeight() {
        return weight;
    }


    public void setWeight(double weight) {
        this.weight = weight;
    }


    public Date getEntry_date() {
        return entryDate;
    }


    public void setEntry_date(Date entry_date) {
        this.entryDate = entry_date;
    }
    
}

