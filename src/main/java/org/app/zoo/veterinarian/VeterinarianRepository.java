package org.app.zoo.veterinarian;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import io.swagger.v3.oas.annotations.media.Schema;

@Repository
@Schema(description = "Veterinarian interface to interact with the DB")

public interface VeterinarianRepository extends JpaRepository<Veterinarian, Integer>, JpaSpecificationExecutor<Veterinarian> {

}
