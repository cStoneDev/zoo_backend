package org.app.zoo.servicetype;

public record ServiceTypeSearchCriteria(
    String searchField,
    int pageNumber,
    int itemsPerPage
) {
    
}
