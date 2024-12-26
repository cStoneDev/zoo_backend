package org.app.zoo.breed.dto.out;

public record BreedOutputDTO(
    int id,
    String name,
    int speciesId,
    String speciesName
) {
    
}