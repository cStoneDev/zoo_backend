package org.app.zoo.contract.dto.out;

import java.sql.Date;

public record ContractOutputDTO(
    int id,
    int providerId,
    Date startingDate,
    Date endingDate,
    Date conciliationDate,
    String description,
    double basePrice
){

}