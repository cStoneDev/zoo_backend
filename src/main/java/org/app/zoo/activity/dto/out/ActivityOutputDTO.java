package org.app.zoo.activity.dto.out;

import java.sql.Date;
import java.sql.Time;

public record ActivityOutputDTO(
    int id,
    int contractId,
    Date date,
    Time time,
    String description
) {
    
}
