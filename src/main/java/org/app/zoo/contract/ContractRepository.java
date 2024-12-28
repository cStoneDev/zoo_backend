package org.app.zoo.contract;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import io.swagger.v3.oas.annotations.media.Schema;

@Repository
@Schema(description = "Contract interface to interact with the DB")
// JpaSpecificationExecutor -> para realizar consultas con especificaciones
public interface ContractRepository extends JpaRepository<Contract, Integer>, JpaSpecificationExecutor<Contract>{
    boolean existsByProviderIdAndStartingDateAndEndingDateAndConciliationDateAndDescriptionAndBasePrice(int providerId, Date startingDate, Date endingDate, Date concilitationDate, String description, double basePrice);
}
