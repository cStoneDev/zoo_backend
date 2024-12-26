package org.app.zoo.providertype;

public record ProviderTypeSearchCriteria(
    String searchField,
    int pageNumber,
    int itemsPerPage
) {
    
}
