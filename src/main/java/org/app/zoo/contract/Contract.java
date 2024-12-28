package org.app.zoo.contract;

import java.sql.Date;

import org.app.zoo.provider.Provider;

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
@Table(name = "contrato")
@Schema(description = "Information about Contract")
public class Contract {  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contrato", nullable = false)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_proveedor", nullable = false)
    private Provider provider;

    @Column(name = "fecha_inicio")
    private Date startingDate;

    @Column(name = "fecha_terminacion")
    private Date endingDate;

    @Column(name = "fecha_conciliacion")
    private Date conciliationDate;

    @Column(name = "descripcion")
    private String description;

    @Column(name = "precio_base")
    private double basePrice;

    public Contract() {
    }
    
    public Contract(Provider provider, Date startingDate, Date endingDate, Date conciliationDate, String description,
            double basePrice) {
        this.provider = provider;
        this.startingDate = startingDate;
        this.endingDate = endingDate;
        this.conciliationDate = conciliationDate;
        this.description = description;
        this.basePrice = basePrice;
    }

    public Contract(int id, Provider provider, Date startingDate, Date endingDate, Date conciliationDate,
            String description, double basePrice) {
        this.id = id;
        this.provider = provider;
        this.startingDate = startingDate;
        this.endingDate = endingDate;
        this.conciliationDate = conciliationDate;
        this.description = description;
        this.basePrice = basePrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public Date getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(Date startingDate) {
        this.startingDate = startingDate;
    }

    public Date getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(Date endingDate) {
        this.endingDate = endingDate;
    }

    public Date getConciliationDate() {
        return conciliationDate;
    }

    public void setConciliationDate(Date conciliationDate) {
        this.conciliationDate = conciliationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    
}
