package org.app.zoo.activity;

import java.sql.Date;
import java.sql.Time;

import org.app.zoo.contract.Contract;

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
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "actividad")
@Schema(description = "Details about an activity")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_actividad", nullable = false)
    private int id;

    @Size(min=3) 
    @Column(name = "descripcion_actividad", nullable = false)
    private String description;

    @Column(name = "fecha")
    private Date date;

    @Column(name = "hora")
    private Time time;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_contrato", nullable = false)
    private Contract contract;

    public Activity() {
    }

    public Activity(int id, @Size(min = 3) String description, Date date, Time time, Contract contract) {
        this.id = id;
        this.description = description;
        this.date = date;
        this.time = time;
        this.contract = contract;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    
}
