package org.app.zoo.breed;

public record BreedSearchCriteria(
    String searchField,
    int pageNumber,
    int itemsPerPage
) {
    
}
