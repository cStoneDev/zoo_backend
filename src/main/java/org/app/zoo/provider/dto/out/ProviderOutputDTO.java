package org.app.zoo.provider.dto.out;

public record ProviderOutputDTO(
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
    String providerTypeName
) implements ProviderResponseDTO{
    
}
