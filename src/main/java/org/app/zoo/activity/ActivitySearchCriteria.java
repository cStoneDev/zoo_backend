package org.app.zoo.activity;

public record ActivitySearchCriteria(
    String searchField,
    int providerTypeId,
    int contractId,
    int activitiesState, // 1 activos, 2 no activos, 0 no aplica
    int pageNumber,
    int itemsPerPage
) {
} 
