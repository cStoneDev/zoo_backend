package org.app.zoo.provider;

import org.app.zoo.providertype.ProviderType;
import org.app.zoo.province.Province;
import org.app.zoo.servicetype.ServiceType;

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
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

@Entity
@Schema(description = "Details about a provider")
@Table(name = "proveedor")
public class Provider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proveedor", nullable = false)
    private int id;

    @Size(min=3) //Name cannot be less than 3 characters
    @Column(name = "nombre", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_provincia", nullable = false)
    private Province province;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_tipo_servicio", nullable = false)
    private ServiceType serviceType;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_tipo_proveedor", nullable = false)
    private ProviderType providerType;

    @Size(min=3) 
    @Column(name = "direccion", nullable = false)
    private String address;

    @Size(min = 8, max = 8) 
    @Column(name = "telefono", nullable = false)
    private String phone;

    @Email
    @Column(name = "email", nullable = false)
    private String email;

    @Size(min=3) 
    @Column(name = "nombre_responsable", nullable = false)
    private String responsibleName;

    protected Provider(){}

    public Provider(int id,String name, Province province, ServiceType serviceType,
            ProviderType providerType, String address,String phone,
            String email, String responsibleName) {
        this.id = id;
        this.name = name;
        this.province = province;
        this.serviceType = serviceType;
        this.providerType = providerType;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.responsibleName = responsibleName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public ProviderType getProviderType() {
        return providerType;
    }

    public void setProviderType(ProviderType providerType) {
        this.providerType = providerType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getResponsibleName() {
        return responsibleName;
    }

    public void setResponsibleName(String responsibleName) {
        this.responsibleName = responsibleName;
    }




}
