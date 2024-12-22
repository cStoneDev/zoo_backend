package org.app.zoo.specie;

import java.util.Optional;

import org.app.zoo.breed.Breed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.swagger.v3.oas.annotations.media.Schema;

@Repository
@Schema(description = "Specie interface to interact with the DB")
public interface SpecieRepository extends JpaRepository<Specie, Integer> {
    Optional<Breed> findByName(String name);
}
