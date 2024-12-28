package org.app.zoo.animal;

import java.sql.Date;
import java.time.LocalDate;

import org.app.zoo.animal.dto.in.AnimalInputDTO;
import org.app.zoo.animal.dto.out.AnimalOutputDTO;
import org.app.zoo.breed.Breed;
import org.app.zoo.breed.BreedRepository;
import org.app.zoo.config.GlobalExceptionHandler;
import org.app.zoo.config.errorHandling.ConstraintViolationException;
import org.app.zoo.config.errorHandling.InvalidInputException;
import org.app.zoo.config.errorHandling.ResourceAlreadyExistsException;
import org.app.zoo.config.errorHandling.ResourceNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import io.swagger.v3.oas.annotations.media.Schema;

@Service
@Schema(description = "Animal service who has the implementations of crud functions and more")
public class AnimalService {

    private final AnimalRepository animalRepository;
    private final BreedRepository breedRepository;
    private final GlobalExceptionHandler globalExceptionHandler;

    public AnimalService(GlobalExceptionHandler globalExceptionHandler, AnimalRepository animalRepository,
            BreedRepository breedRepository) {
        this.animalRepository = animalRepository;
        this.breedRepository = breedRepository;
        this.globalExceptionHandler = globalExceptionHandler;
    }

    public AnimalOutputDTO createAnimal(AnimalInputDTO animalInputDTO) {
        Breed breed = breedRepository.findById(animalInputDTO.breedId())
                .orElseThrow(() -> new ResourceNotFoundException("Raza no encontrada"));

        Animal animal = new Animal();
        if (animalInputDTO.name() == null || animalInputDTO.name().isEmpty()) {
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

        try {
            Animal savedAnimal = animalRepository.save(animal);
            return mapToOutputDTO(savedAnimal);
        } catch (Exception e) {
            throw new InvalidInputException(globalExceptionHandler.extractErrorMessage(e.getMessage()));
        }

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
                animal.getEntry_date());
        return animalOutputDTO;
    }

    public Page<AnimalOutputDTO> searchAnimals(AnimalSearchCriteria criteria) {
        // Todo esta validado en animalSpecification

        Specification<Animal> spec = Specification.where(null);

        // Aplicar cada filtro si es válido
        if (criteria.searchField() != null && !criteria.searchField().isEmpty()) {
            spec = spec.and(AnimalSpecification.filterBySearchField(criteria.searchField()));
        }
        if (criteria.breedId() > 0) {
            spec = spec.and(AnimalSpecification.filterByBreed(criteria.breedId()));
        }
        if (criteria.speciesId() > 0) {
            spec = spec.and(AnimalSpecification.filterBySpecies(criteria.speciesId()));
        }
        if (criteria.minAge() > 0 && criteria.maxAge() > 0 && criteria.minAge() <= criteria.maxAge()) {
            spec = spec.and(AnimalSpecification.filterByAge(criteria.minAge(), criteria.maxAge()));
        }
        if (criteria.minDaysInShelter() > 0 && criteria.maxDaysInShelter() > 0
                && criteria.minDaysInShelter() <= criteria.maxDaysInShelter()) {
            spec = spec.and(AnimalSpecification.filterByDaysInShelter(criteria.minDaysInShelter(),
                    criteria.maxDaysInShelter()));
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

    public void deleteAnimal(int id) {

        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Animal no encontrado"));

        try {
            animalRepository.delete(animal);
        } catch (DataIntegrityViolationException e) {
            throw new ConstraintViolationException(
                    "No se puede eliminar el animal porque tiene dependencias relacionadas.");
        }
    }

    public AnimalOutputDTO findById(int id) {
        return mapToOutputDTO(animalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Animal no encontrado")));
    }

    public AnimalOutputDTO updateAnimal(int id, AnimalInputDTO updatedAnimal) {
        // Verificar si el animal existe
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Animal no encontrado"));

        // Validar que el nombre no esté vacío
        if (updatedAnimal.name() == null || updatedAnimal.name().isEmpty()) {
            throw new InvalidInputException("El nombre del animal no puede estar vacío");
        }

        // Validar que la edad sea un valor razonable
        if (updatedAnimal.age() < 0) {
            throw new InvalidInputException("La edad del animal no puede ser negativa");
        }

        // Validar que el peso sea un valor razonable
        if (updatedAnimal.weight() < 0) {
            throw new InvalidInputException("El peso del animal no puede ser negativo");
        }

        // Validar que la fecha de entrada sea válida (no futura)
        if (updatedAnimal.entryDate().after(new java.util.Date())) {
            throw new InvalidInputException("La fecha de entrada no puede estar en el futuro");
        }

        // Verificar si ya existe un animal con las mismas características
        boolean exists = animalRepository.existsByNameAndAgeAndWeightAndBreedIdAndEntryDate(
                updatedAnimal.name(),
                updatedAnimal.age(),
                updatedAnimal.weight(),
                updatedAnimal.breedId(),
                updatedAnimal.entryDate());

        if (exists) {
            throw new ResourceAlreadyExistsException("Ya existe un animal con las mismas características.");
        }

        // Validar que la raza con el ID proporcionado exista
        Breed breed = breedRepository.findById(updatedAnimal.breedId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Raza no encontrada con el ID: " + updatedAnimal.breedId()));

        // Actualizar los valores del animal
        animal.setName(updatedAnimal.name());
        animal.setAge(updatedAnimal.age());
        animal.setWeight(updatedAnimal.weight());
        animal.setBreed(breed); // Actualizamos la raza
        animal.setEntry_date(updatedAnimal.entryDate()); // Actualizamos la fecha de entrada

        // Guardar y devolver el animal actualizado
        try {
            return mapToOutputDTO(animalRepository.save(animal));
        } catch (Exception e) {
            throw new InvalidInputException(globalExceptionHandler.extractErrorMessage(e.getMessage()));
        }
        
    }
}
