package org.app.zoo.breed;


import org.app.zoo.breed.dto.in.BreedInputDTO;
import org.app.zoo.breed.dto.out.BreedOutputDTO;
import org.app.zoo.config.errorHandling.ConstraintViolationException;
import org.app.zoo.config.errorHandling.InvalidInputException;
import org.app.zoo.config.errorHandling.ResourceAlreadyExistsException;
import org.app.zoo.config.errorHandling.ResourceNotFoundException;
import org.app.zoo.species.Species;
import org.app.zoo.species.SpeciesRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import io.swagger.v3.oas.annotations.media.Schema;

@Service
@Schema(description = "Breed service who has the implementations of crud functions and more")
public class BreedService {
    
    private final BreedRepository breedRepository;
    private final SpeciesRepository speciesRepository;

    public BreedService(BreedRepository breedRepository, SpeciesRepository speciesRepository) {
        this.breedRepository = breedRepository;
        this.speciesRepository = speciesRepository;
    }

    public BreedOutputDTO createBreed(BreedInputDTO breed) {
        Species species = speciesRepository.findById(breed.speciesId())
            .orElseThrow(() -> new ResourceNotFoundException("Especie no encontrada"));
        Breed breedToSave = new Breed(
            breed.name(), 
            species);
        breedRepository.save(breedToSave);
        return mapToOutputDTO(breedToSave);
    }

    private BreedOutputDTO mapToOutputDTO(Breed breed) {
        BreedOutputDTO breedOutputDTO = new BreedOutputDTO(
            breed.getId_breed(),
            breed.getName(),
            breed.getSpecies().getId_species(),
            breed.getSpecies().getName()
        );
        return breedOutputDTO;
    }

    public void deleteBreed(int id) {
        // Verificar si la raza existe
        Breed breed = breedRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Raza no encontrada"));

        try {
            breedRepository.delete(breed);
        } catch (DataIntegrityViolationException e) {
            throw new ConstraintViolationException("No se puede eliminar la raza porque tiene dependencias relacionadas.");
        }
    }

    public BreedOutputDTO findById(int id) {
        return mapToOutputDTO(breedRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Raza no encontrada")));
    }

    public BreedOutputDTO updateBreed(int id, BreedInputDTO updatedBreed) {
        // Verificar si la raza existe
        Breed breed = breedRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Raza no encontrada"));
    
        // Validar que el nombre no esté vacío
        if (updatedBreed.name() == null || updatedBreed.name().isEmpty()) {
            throw new InvalidInputException("El nombre de la raza no puede estar vacío");
        }
    
        // Validar que no exista otra raza con el mismo nombre
        boolean exists = breedRepository.existsByNameAndIdNot(updatedBreed.name(), id);
        if (exists) {
            throw new ResourceAlreadyExistsException("Ya existe una raza con el nombre: " + updatedBreed.name());
        }
    
        // Validar que la especie con el ID proporcionado exista
        Species species = speciesRepository.findById(updatedBreed.speciesId())
                .orElseThrow(() -> new ResourceNotFoundException("Especie no encontrada con el ID: " + updatedBreed.speciesId()));
    
        // Actualizar los valores de la raza
        breed.setName(updatedBreed.name());
        breed.setSpecies(species);  // Actualizamos la especie
    
        // Guardar y devolver la raza actualizada
        breedRepository.save(breed);
        return mapToOutputDTO(breed);
    }
    


    public Page<BreedOutputDTO> getAllBreed(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("id")); // Crear un objeto Pageable
    
        // Obtener la lista de speciees según la paginación
        Page<Breed> breedPage = breedRepository.findAll(pageable);
    
        // Convertir a DTOs
        return breedPage.map(this::mapToOutputDTO);
    }

    public Page<BreedOutputDTO> searchBreed(BreedSearchCriteria criteria) {
        // Todo esta validado en breedSpecification

        Specification<Breed> spec = Specification.where(null);

        // Aplicar cada filtro si es válido
        if (criteria.searchField() != null && !criteria.searchField().isEmpty()){
            spec = spec.and(BreedSpecification.filterBySearchField(criteria.searchField()));
        }
        // Crear un objeto Pageable usando pageNumber y itemsPerPage
        Pageable pageable = PageRequest.of(criteria.pageNumber(), criteria.itemsPerPage(), Sort.by("id"));
        
        // Obtener la lista de speciees según la especificación y la paginación
        Page<Breed> breedPage = breedRepository.findAll(spec, pageable);
        
        // Convertir a DTOs
        return breedPage.map(this::mapToOutputDTO);
    }
}
