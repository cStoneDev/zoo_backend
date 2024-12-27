package org.app.zoo.province;

public record ProvinceSearchCriteria(
    String searchField,
    int pageNumber,
    int itemsPerPage
) {
    
}
