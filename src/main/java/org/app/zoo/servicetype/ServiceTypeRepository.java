package org.app.zoo.servicetype;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import io.swagger.v3.oas.annotations.media.Schema;

@Repository
@Schema(description = "Animal interface to interact with the DB")
public interface ServiceTypeRepository extends JpaRepository<ServiceType, Integer> , JpaSpecificationExecutor<ServiceType>{
    boolean existsByNameAndIdNot(String name, int id);
}
