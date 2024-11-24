package org.app.zoo.model;

import io.swagger.annotations.ApiModel;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@ApiModel(description = "Details about an animal")
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id_animal;

    private String nombre;
    private int id_raza;
    private int edad;
    private double peso;
    private int dias_zoo;

    public Animal(){}

    public Animal(int id_animal, String nombre, int id_raza, int edad, double peso, int dias_zoo) {
        setId_animal(id_animal);
        setNombre(nombre);
        setId_raza(id_raza);
        setEdad(edad);
        setPeso(peso);
        setDias_zoo(dias_zoo);
    }


    public int getId_animal() {
        return id_animal;
    }
    public void setId_animal(int id_animal) {
        this.id_animal = id_animal;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public int getId_raza() {
        return id_raza;
    }
    public void setId_raza(int id_raza) {
        this.id_raza = id_raza;
    }
    public int getEdad() {
        return edad;
    }
    public void setEdad(int edad) {
        this.edad = edad;
    }
    public double getPeso() {
        return peso;
    }
    public void setPeso(double peso) {
        this.peso = peso;
    }
    public int getDias_zoo() {
        return dias_zoo;
    }
    public void setDias_zoo(int dias_zoo) {
        this.dias_zoo = dias_zoo;
    }

    
}

