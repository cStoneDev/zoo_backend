package org.app.zoo.clinic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import io.swagger.v3.oas.annotations.media.Schema;

@Repository
@Schema(description = "Clinic interface to interact with the DB")
public interface ClinicRepository extends JpaRepository<Clinic, Integer>, JpaSpecificationExecutor<Clinic> {
    boolean existsByNameAndIdNot(String name, int id);
}
