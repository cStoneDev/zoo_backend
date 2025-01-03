package org.app.zoo.species;

import java.util.Optional;

import org.app.zoo.breed.Breed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import io.swagger.v3.oas.annotations.media.Schema;

@Repository
@Schema(description = "Species interface to interact with the DB")
public interface SpeciesRepository extends JpaRepository<Species, Integer>, JpaSpecificationExecutor<Species> {
    Optional<Breed> findByName(String name);
    boolean existsByNameAndIdNot(String name, int id);
}
