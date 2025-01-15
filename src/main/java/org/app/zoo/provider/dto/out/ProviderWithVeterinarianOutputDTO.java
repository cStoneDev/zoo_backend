package org.app.zoo.provider.dto.out;

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
    String fax,
    double cityDistance,
    int clinicId,
    int specialityId,
    String clinicName,
    String specialityName
) implements ProviderResponseDTO{
} 