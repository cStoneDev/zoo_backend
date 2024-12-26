package org.app.zoo.animal.dto.in;

import java.sql.Date;

public record AnimalInputDTO(
    String name,
    int age,
    double weight,
    int breedId,
    Date entryDate

) {
    
}