package org.app.zoo.provider.dto.out;

import org.app.zoo.veterinarian.dto.out.VeterinarianOutputDTO;

public record ProviderWithVeterinarianOutputDTO(
    int id,
    String name,
    int provinceId,
    int serviceTypeId,
    int providerTypeId,
    String address,
    String phone,
    String email,
    String responsibleName,
    String provinceName,
    String serviceTypeName,
    String providerTypeName,
    VeterinarianOutputDTO veterinarianOutputDTO
) implements ProviderResponseDTO{
} 