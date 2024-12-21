package org.app.zoo.animal;

import java.sql.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;

@Entity
@Schema(description = "Details about an animal")
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id_animal;

    @Size(min=3) //Name cannot be less than 3 characters
    private String nombre;

    private int id_raza;
    private int edad;
    private double peso;
    private Date fecha_ingreso;

    protected Animal(){}

    public Animal(int id_animal, String nombre, int id_raza, int edad, double peso, Date fecha_ingreso) {
        setId_animal(id_animal);
        setNombre(nombre);
        setId_raza(id_raza);
        setEdad(edad);
        setPeso(peso);
        setDias_zoo(fecha_ingreso);
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
    public Date getDias_zoo() {
        return fecha_ingreso;
    }
    public void setDias_zoo(Date fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }

    
}

