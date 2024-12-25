package org.app.zoo.animal;

public record AnimalSearchCriteria(
    String searchField,
    int breedId,
    int speciesId,
    int minAge,
    int maxAge,
    double minWeight,
    double maxWeight,
    int minDaysInShelter,
    int maxDaysInShelter,
    int pageNumber, // Número de página
    int itemsPerPage // Tamaño de la página
) {
}
