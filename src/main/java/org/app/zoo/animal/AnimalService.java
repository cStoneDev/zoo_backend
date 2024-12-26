package org.app.zoo.animal;

import java.sql.Date;
import java.time.LocalDate;

import org.app.zoo.animal.dto.in.AnimalInputDTO;
import org.app.zoo.animal.dto.out.AnimalOutputDTO;
import org.app.zoo.breed.Breed;
import org.app.zoo.breed.BreedRepository;
import org.app.zoo.config.errorHandling.InvalidInputException;
import org.app.zoo.config.errorHandling.ResourceNotFoundException;
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
        Breed breed = breedRepository.findById(animalInputDTO.breedId())
                .orElseThrow(() -> new ResourceNotFoundException("Raza no encontrada"));

        Animal animal = new Animal();
        if (animalInputDTO.name() == null || animalInputDTO.name().isEmpty()){
            throw new InvalidInputException("El nombre del animal no puede estar vacío");
        }
        animal.setName(animalInputDTO.name());
        // la edad puede o no tenerla
        animal.setAge(animalInputDTO.age());
        // el peso puede o no tenerlo
        animal.setWeight(animalInputDTO.weight());
        // breed ya validada
        animal.setBreed(breed);

        // Assign an entryDate if any
        if (animalInputDTO.entryDate() != null) {
            animal.setEntry_date(animalInputDTO.entryDate());
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
        AnimalOutputDTO animalOutputDTO = new AnimalOutputDTO(
            animal.getId_animal(),
            animal.getName(),
            animal.getAge(),
            animal.getWeight(),
            animal.getBreed().getName(),
            animal.getBreed().getSpecies().getName(),
            animal.getBreed().getId_breed(),
            animal.getBreed().getSpecies().getId_species(),
            animal.getEntry_date()
        );
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

