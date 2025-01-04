package org.app.zoo.programation;

import org.app.zoo.animal.Animal;

import org.app.zoo.activity.Activity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Schema(description = "Programation info")
@Table(name = "programacion")
public class Programation{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_programacion")
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_actividad", nullable = false)
    private Activity activity;

    @ManyToOne
    @JoinColumn(name = "id_animal", nullable = false)
    private Animal animal;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Programation(int id, Activity activity, Animal animal) {
        this.id = id;
        this.activity = activity;
        this.animal = animal;
    }

    public Programation(Activity activity, Animal animal) {
        this.activity = activity;
        this.animal = animal;
    }

    public Programation() {
    }

}
