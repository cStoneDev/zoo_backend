package org.app.zoo.animal;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.app.zoo.animal.dto.in.AnimalInputDTO;
import org.app.zoo.animal.dto.out.AnimalOutputDTO;
import org.app.zoo.breed.Breed;
import org.app.zoo.breed.BreedRepository;
import org.springframework.stereotype.Service;

import io.swagger.v3.oas.annotations.media.Schema;

@Service
@Schema(description = "Animal service who has the implementations of crud functions and more")
public class AnimalService {

    private final AnimalRepository animalRepository;
    private final BreedRepository breedRepository;

    public AnimalService(AnimalRepository animalRepository, BreedRepository breedRepository) {
        this.animalRepository = animalRepository;
        this.breedRepository = breedRepository;
    }

    public AnimalOutputDTO createAnimal(AnimalInputDTO animalInputDTO) {
        Breed breed = breedRepository.findById(animalInputDTO.getBreedId())
                .orElseThrow(() -> new RuntimeException("Breed not found"));

        Animal animal = new Animal();
        animal.setName(animalInputDTO.getName());
        animal.setAge(animalInputDTO.getAge());
        animal.setWeight(animalInputDTO.getWeight());
        animal.setBreed(breed);

        // Assign an entryDate if any
        if (animalInputDTO.getEntryDate() != null) {
            animal.setEntry_date(animalInputDTO.getEntryDate());
        } else {
            animal.setEntry_date(Date.valueOf(LocalDate.now())); // Fecha actual
        }

        Animal savedAnimal = animalRepository.save(animal);

        return mapToOutputDTO(savedAnimal);
    }

    public List<AnimalOutputDTO> getAllAnimals() {
        return animalRepository.findAll().stream()
                .map(this::mapToOutputDTO)
                .collect(Collectors.toList());
    }

    private AnimalOutputDTO mapToOutputDTO(Animal animal) {
        AnimalOutputDTO animalOutputDTO = new AnimalOutputDTO();
        animalOutputDTO.setId(animal.getId_animal());
        animalOutputDTO.setName(animal.getName());
        animalOutputDTO.setAge(animal.getAge());
        animalOutputDTO.setWeight(animal.getWeight());
        animalOutputDTO.setBreedName(animal.getBreed().getName());
        animalOutputDTO.setSpecieName(animal.getBreed().getSpecie().getName());
        animalOutputDTO.setBreedId(animal.getBreed().getId_breed());
        animalOutputDTO.setSpecieId(animal.getBreed().getSpecie().getId_specie());
        animalOutputDTO.setEntryDate(animal.getEntry_date());
        return animalOutputDTO;
    }
}
