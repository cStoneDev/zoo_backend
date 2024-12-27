package org.app.zoo.speciality;

public record SpecialitySearchCriteria(
    String searchField,
    int pageNumber,
    int itemsPerPage
) {
    
}
