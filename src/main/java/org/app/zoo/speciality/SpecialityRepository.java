package org.app.zoo.speciality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import io.swagger.v3.oas.annotations.media.Schema;
@Repository
@Schema(description = "Speciality interface to interact with the DB")
public interface SpecialityRepository extends JpaRepository<Speciality, Integer>, JpaSpecificationExecutor<Speciality> {
    boolean existsByNameAndIdNot(String name, int id);
}
