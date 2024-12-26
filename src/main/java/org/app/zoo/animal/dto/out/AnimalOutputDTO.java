package org.app.zoo.animal.dto.out;

import java.sql.Date;

public record AnimalOutputDTO (
    int id,
    String name,
    int age,
    double weight,
    String breedName,
    String speciesName,
    int speciesId,
    int breedId,
    Date entryDate
) {
    
}