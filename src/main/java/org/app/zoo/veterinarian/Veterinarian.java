package org.app.zoo.veterinarian;

import org.app.zoo.clinic.Clinic;
import org.app.zoo.provider.Provider;
import org.app.zoo.speciality.Speciality;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

@Entity
@Schema(description = "Details about a veterinarian")
@Table(name = "veterinario")
public class Veterinarian {
    @Id
    @Column(name = "id_proveedor")
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE) // Relación uno a uno
    @MapsId // Indica que se comparte la clave primaria
    @JoinColumn(name = "id_proveedor", nullable = false)
    private Provider provider;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_clinica", nullable = false)
    private Clinic clinic;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_especialidad", nullable = false)
    private Speciality speciality;

    @Size(min=3, max = 20)
    @Column(name = "fax", nullable = false)
    private String fax;

    @Column(name = "distancia_ciudad", nullable = false)
    private double cityDistance;

    public Veterinarian(){};
    public Veterinarian(Provider provider, Clinic clinic, Speciality speciality, @Size(min = 3, max = 20) String fax,
            double cityDistance) {
        this.provider = provider;
        this.clinic = clinic;
        this.speciality = speciality;
        this.fax = fax;
        this.cityDistance = cityDistance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public double getCityDistance() {
        return cityDistance;
    }

    public void setCityDistance(double cityDistance) {
        this.cityDistance = cityDistance;
    }

    
}
