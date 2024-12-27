package org.app.zoo.clinic;

public record ClinicSearchCriteria(
    String searchField,
    int pageNumber,
    int itemsPerPage
) {
    
}
