package org.app.zoo.animal;

import java.sql.Date;
import java.time.LocalDate;

import org.app.zoo.animal.dto.in.AnimalInputDTO;
import org.app.zoo.animal.dto.out.AnimalOutputDTO;
import org.app.zoo.breed.Breed;
import org.app.zoo.breed.BreedRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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

    public Page<AnimalOutputDTO> getAllAnimals(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("id")); // Crear un objeto Pageable
    
        // Obtener la lista de animales según la paginación
        Page<Animal> animalsPage = animalRepository.findAll(pageable);
    
        // Convertir a DTOs
        return animalsPage.map(this::mapToOutputDTO);
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

    public Page<AnimalOutputDTO> searchAnimals(AnimalSearchCriteria criteria) {
        // Todo esta validado en animalSpecification

        Specification<Animal> spec = Specification.where(null);

        // Aplicar cada filtro si es válido
        if (criteria.searchField() != null && !criteria.searchField().isEmpty()){
            spec = spec.and(AnimalSpecification.filterBySearchField(criteria.searchField()));
        }
        if (criteria.breedId() > 0) {
            spec = spec.and(AnimalSpecification.filterByBreed(criteria.breedId()));
        }
        if (criteria.speciesId() > 0){
            spec = spec.and(AnimalSpecification.filterBySpecies(criteria.speciesId()));
        }
        if (criteria.minAge() > 0 && criteria.maxAge() > 0 && criteria.minAge() <= criteria.maxAge()) {
            spec = spec.and(AnimalSpecification.filterByAge(criteria.minAge(), criteria.maxAge()));
        }
        if (criteria.minDaysInShelter() > 0 && criteria.maxDaysInShelter() > 0 
            && criteria.minDaysInShelter() <= criteria.maxDaysInShelter()) {
            spec = spec.and(AnimalSpecification.filterByDaysInShelter(criteria.minDaysInShelter(), criteria.maxDaysInShelter()));
        }
        if (criteria.minWeight() > 0 && criteria.maxWeight() > 0 && criteria.minWeight() <= criteria.maxWeight()) {
            spec = spec.and(AnimalSpecification.filterByWeight(criteria.minWeight(), criteria.maxWeight()));
        }

        // Crear un objeto Pageable usando pageNumber y itemsPerPage
        Pageable pageable = PageRequest.of(criteria.pageNumber(), criteria.itemsPerPage(), Sort.by("id"));
        
        // Obtener la lista de animales según la especificación y la paginación
        Page<Animal> animalsPage = animalRepository.findAll(spec, pageable);
        
        // Convertir a DTOs
        return animalsPage.map(this::mapToOutputDTO);
    }
}

