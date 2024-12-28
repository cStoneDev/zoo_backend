package org.app.zoo.provider.dto.in;

public record ProviderInputDTO(
    String name,
    int provinceId,
    String provinceName,
    int serviceTypeId,
    String serviceTypeName,
    int providerTypeId,
    String providerTypeName,
    String address,
    String phone,
    String email,
    String responsibleName,
    String fax,           // Optional
    Double cityDistance,   // Optional
    Integer clinicId,      // Optional
    Integer specialityId  // Optional
) {
} 
