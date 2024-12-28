package org.app.zoo.species;

import org.app.zoo.config.GlobalExceptionHandler;
import org.app.zoo.config.errorHandling.ConstraintViolationException;
import org.app.zoo.config.errorHandling.InvalidInputException;
import org.app.zoo.config.errorHandling.ResourceAlreadyExistsException;
import org.app.zoo.config.errorHandling.ResourceNotFoundException;
import org.app.zoo.species.dto.in.SpeciesInputDTO;
import org.app.zoo.species.dto.out.SpeciesOutputDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import io.swagger.v3.oas.annotations.media.Schema;

@Service
@Schema(description = "Species service who has the implementations of crud functions and more")
public class SpeciesService {

    private final SpeciesRepository speciesRepository;
    private final GlobalExceptionHandler globalExceptionHandler;

    public SpeciesService(GlobalExceptionHandler globalExceptionHandler, SpeciesRepository speciesRepository) {
        this.globalExceptionHandler = globalExceptionHandler;
        this.speciesRepository = speciesRepository;
    }

    public SpeciesOutputDTO createSpecies(SpeciesInputDTO species) {
        if (species.name() == null || species.name().isEmpty()) {
            throw new InvalidInputException("El nombre de la especie no puede estar vacío");
        }
        Species speciesSave = new Species(species.name());

        try {
            speciesRepository.save(speciesSave);
            return mapToOutputDTO(speciesSave);
        } catch (Exception e) {
            throw new InvalidInputException(globalExceptionHandler.extractErrorMessage(e.getMessage()));
        }

    }

    public void deleteSpecies(int id) {
        // Verificar si la especie existe
        Species species = speciesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Especie no encontrada"));

        try {
            speciesRepository.delete(species);
        } catch (DataIntegrityViolationException e) {
            throw new ConstraintViolationException(
                    "No se puede eliminar la especie porque tiene dependencias relacionadas.");
        }
    }

    public SpeciesOutputDTO findById(int id) {
        return mapToOutputDTO(speciesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Especie no encontrada")));
    }

    public SpeciesOutputDTO updateSpecies(int id, SpeciesInputDTO updatedSpecies) {
        // Verificar si la especie existe
        Species species = speciesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Especie no encontrada"));

        // Validar que el nombre no esté vacío
        if (updatedSpecies.name() == null || updatedSpecies.name().isEmpty()) {
            throw new InvalidInputException("El nombre de la especie no puede estar vacío");
        }

        // Validar que no exista otra especie con el mismo nombre
        boolean exists = speciesRepository.existsByNameAndIdNot(updatedSpecies.name(), id);
        if (exists) {
            throw new ResourceAlreadyExistsException("Ya existe una especie con el nombre: " + updatedSpecies.name());
        }

        // Actualizar los valores de la especie
        species.setName(updatedSpecies.name());
        // (Actualizar otros campos según corresponda)

        // Guardar y devolver la especie actualizada

        try {
            speciesRepository.save(species);
            return mapToOutputDTO(species);
        } catch (Exception e) {
            throw new InvalidInputException(globalExceptionHandler.extractErrorMessage(e.getMessage()));
        }

    }

    public Page<SpeciesOutputDTO> getAllSpecies(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("id")); // Crear un objeto Pageable

        // Obtener la lista de speciees según la paginación
        Page<Species> speciesPage = speciesRepository.findAll(pageable);

        // Convertir a DTOs
        return speciesPage.map(this::mapToOutputDTO);
    }

    private SpeciesOutputDTO mapToOutputDTO(Species species) {
        SpeciesOutputDTO speciesOutputDTO = new SpeciesOutputDTO(
                species.getId_species(),
                species.getName());
        return speciesOutputDTO;
    }

    public Page<SpeciesOutputDTO> searchSpecies(SpeciesSearchCriteria criteria) {
        // Todo esta validado en speciesSpecification

        Specification<Species> spec = Specification.where(null);

        // Aplicar cada filtro si es válido
        if (criteria.searchField() != null && !criteria.searchField().isEmpty()) {
            spec = spec.and(SpeciesSpecification.filterBySearchField(criteria.searchField()));
        }
        // Crear un objeto Pageable usando pageNumber y itemsPerPage
        Pageable pageable = PageRequest.of(criteria.pageNumber(), criteria.itemsPerPage(), Sort.by("id"));

        // Obtener la lista de speciees según la especificación y la paginación
        Page<Species> speciesPage = speciesRepository.findAll(spec, pageable);

        // Convertir a DTOs
        return speciesPage.map(this::mapToOutputDTO);
    }

}
