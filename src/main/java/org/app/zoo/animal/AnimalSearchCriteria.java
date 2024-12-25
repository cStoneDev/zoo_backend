package org.app.zoo.animal;

public record AnimalSearchCriteria(
    int breedId,
    int minAge,
    int maxAge,
    double minWeight,
    double maxWeight,
    int minDaysInShelter,
    int maxDaysInShelter
) {
}
