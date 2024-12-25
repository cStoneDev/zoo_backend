package org.app.zoo.animal;

import org.springframework.stereotype.Repository;

import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
@Repository
@Schema(description = "Animal interface to interact with the DB")
// JpaSpecificationExecutor -> para realizar consultas con especificaciones
public interface AnimalRepository extends JpaRepository<Animal, Integer>, JpaSpecificationExecutor<Animal>{
    
}
