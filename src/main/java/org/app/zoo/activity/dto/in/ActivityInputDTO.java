package org.app.zoo.activity.dto.in;

import java.sql.Date;
import java.sql.Time;

public record ActivityInputDTO(
    int contractId,
    Date date,
    Time time,
    String description
) {
}
