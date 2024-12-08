package org.app.zoo.repository;

import org.springframework.stereotype.Repository;

import io.swagger.annotations.ApiModel;

import org.app.zoo.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
@Repository
@ApiModel(description = "Animal interface to interact with the DB")
public interface AnimalRepository extends JpaRepository<Animal, Integer>{
    
}
