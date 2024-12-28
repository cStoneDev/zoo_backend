package org.app.zoo.contract.dto.in;

import java.sql.Date;

public record ContractInputDTO(
    int providerId,
    Date startingDate,
    Date endingDate,
    Date conciliationDate,
    String description,
    double basePrice
) {
    
}
