package org.app.zoo.providertype;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.swagger.v3.oas.annotations.media.Schema;

@Repository
@Schema(description = "Animal interface to interact with the DB")
public interface ProviderTypeRepository extends JpaRepository<ProviderType, Integer>{
    
}
