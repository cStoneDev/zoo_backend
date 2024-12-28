package org.app.zoo.provider;

public record ProviderSearchCriteria(
    String searchField,
    int pageNumber,
    int itemsPerPage 
) {
}
