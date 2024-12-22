package org.app.zoo.breed;

import java.util.List;

import org.springframework.stereotype.Service;

import io.swagger.v3.oas.annotations.media.Schema;

@Service
@Schema(description = "Breed service who has the implementations of crud functions and more")
public class BreedService {
    
    private final BreedRepository breedRepository;

    public BreedService(BreedRepository breedRepository) {
        this.breedRepository = breedRepository;
    }

    public Breed createBreed(Breed breed) {
        return breedRepository.save(breed);
    }

    public List<Breed> getAllBreeds() {
        return breedRepository.findAll();
    }
}
