package org.app.zoo.programation.dto.out;

import java.sql.Time;
import java.sql.Date;

public record ProgramationOutputDTO(
    int id,
    Date date,
    Time time,
    String animalName
) {
    
}
