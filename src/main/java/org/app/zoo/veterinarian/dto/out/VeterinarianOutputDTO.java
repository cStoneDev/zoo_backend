package org.app.zoo.veterinarian.dto.out;

public record VeterinarianOutputDTO(
    String fax,
    double cityDistance,
    int clinicId,
    int specialityId,
    String clinicName,
    String specialityName
) {
}
