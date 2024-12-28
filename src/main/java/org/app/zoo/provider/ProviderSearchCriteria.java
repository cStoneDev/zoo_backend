package org.app.zoo.provider;

public record ProviderSearchCriteria(
    String searchField,
    int provinceId,
    int serviceTypeId,
    int providerTypeId,
    int pageNumber,
    int itemsPerPage 
) {
}
