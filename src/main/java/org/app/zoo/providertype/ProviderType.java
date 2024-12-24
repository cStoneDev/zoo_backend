package org.app.zoo.providertype;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Schema(description = "Details about providers type")
@Table(name = "tipoproveedor")
public class ProviderType {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_proveedor", nullable = false)
    private int id; 

    @Column(name = "nombre", nullable = false)
    private String name;

    public ProviderType(){}

    public ProviderType(int id, String name){
        setId(id);
        setName(name);
    }

    public ProviderType(String name){
        setName(name);
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


    
}
