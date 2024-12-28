package org.app.zoo.veterinarian.dto.in;

public record VeterinarianInputDTO(
    String fax,
    double cityDistance,
    int clinicId,
    int providerId,
    int specialityId
) {
}
