package org.app.zoo.report.dto;

import java.sql.Date;

public record DatesInputDTO(
    String fileType,
    Date fecha_inicio,
    Date fecha_terminacion
) {
}
