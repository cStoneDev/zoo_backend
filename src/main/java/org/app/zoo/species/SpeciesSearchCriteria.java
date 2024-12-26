package org.app.zoo.species;

public record SpeciesSearchCriteria(
    String searchField,
    int pageNumber,
    int itemsPerPage
) {
    
}
