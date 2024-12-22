package org.app.zoo.breed;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.swagger.v3.oas.annotations.media.Schema;

@Repository
@Schema(description = "Breed interface to interact with the DB")
public interface BreedRepository extends JpaRepository<Breed, Integer> {
    Optional<Breed> findByName(String name);
}
