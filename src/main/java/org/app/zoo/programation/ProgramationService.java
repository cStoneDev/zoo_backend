package org.app.zoo.programation;

import java.sql.Time;

import org.app.zoo.activity.Activity;
import org.app.zoo.activity.ActivityRepository;
import org.app.zoo.animal.Animal;
import org.app.zoo.animal.AnimalRepository;
import org.app.zoo.config.GlobalExceptionHandler;
import org.app.zoo.config.errorHandling.ConstraintViolationException;
import org.app.zoo.config.errorHandling.InvalidInputException;
import org.app.zoo.config.errorHandling.ResourceNotFoundException;
import org.app.zoo.programation.dto.in.ProgramationInputDTO;
import org.app.zoo.programation.dto.out.ProgramationOutputDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import io.swagger.v3.oas.annotations.media.Schema;

@Service
@Schema(description = "Programation service who has the implementations of crud functions and more")
public class ProgramationService {
    private final ProgramationRepository programationRepository;
    private final AnimalRepository animalRepository;
    private final ActivityRepository activityRepository;
    private final GlobalExceptionHandler globalExceptionHandler;

    public ProgramationService(ProgramationRepository programationRepository, GlobalExceptionHandler globalExceptionHandler, AnimalRepository animalRepository, ActivityRepository activityRepository) {
        this.programationRepository = programationRepository;
        this.globalExceptionHandler = globalExceptionHandler;
        this.animalRepository = animalRepository;
        this.activityRepository = activityRepository;
    }

    public ProgramationOutputDTO createProgramation(ProgramationInputDTO programationInputDTO) {
        Animal animal = animalRepository.findById(programationInputDTO.animalId())
                .orElseThrow(() -> new ResourceNotFoundException("Animal no encontrado"));

        Activity activity = activityRepository.findById(programationInputDTO.activityId())
                .orElseThrow(() -> new ResourceNotFoundException("Actividad no encontrada"));

        Programation programation = new Programation();
        programation.setAnimal(animal);
        programation.setActivity(activity);

        try {
            Programation savedProgramation = programationRepository.save(programation);
            return mapToOutputDTO(savedProgramation);
        } catch (Exception e) {
            // Captura la excepción lanzada por el trigger
            throw new InvalidInputException(globalExceptionHandler.extractErrorMessage(e.getMessage()));
        } 
    }

    public Page<ProgramationOutputDTO> getAllProgramation(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("id")); // Crear un objeto Pageable
    
        // Obtener la lista de programationes según la paginación
        Page<Programation> programationsPage = programationRepository.findAll(pageable);
    
        // Convertir a DTOs
        return programationsPage.map(this::mapToOutputDTO);
    }

    private ProgramationOutputDTO mapToOutputDTO(Programation programation) {
        ProgramationOutputDTO programationOutputDTO = new ProgramationOutputDTO(
            programation.getId(),
            programation.getActivity().getDate(),
            programation.getActivity().getTime(),
            programation.getAnimal().getName()
        );
        return programationOutputDTO;
    }

    public Page<ProgramationOutputDTO> searchProgramation(ProgramationSearchCriteria criteria) {
        // Todo esta validado en programationSpecification

        Specification<Programation> spec = Specification.where(null);

        // Aplicar cada filtro si es válido
        if (criteria.searchField() != null && !criteria.searchField().isEmpty()){
            spec = spec.and(ProgramationSpecification.filterBySearchField(criteria.searchField()));
        }

        // Filtro por rango de fechas
        if (criteria.minDate() != null && criteria.maxDate() != null) {
            if (criteria.minDate().before(criteria.maxDate())) {
                spec = spec.and(ProgramationSpecification.filterByActivityDateRange(criteria.minDate(), criteria.maxDate()));
            } else {
                throw new IllegalArgumentException("La fecha mínima debe ser anterior a la fecha máxima.");
            }
        }

        // Filtro por rango de Tiempo
        if (criteria.minTime() != null && !criteria.minTime().isEmpty() &&
            criteria.maxTime() != null && !criteria.maxTime().isEmpty()) {
            
            Time minTime = Time.valueOf(criteria.minTime());
            Time maxTime = Time.valueOf(criteria.maxTime());
            
            if (minTime.before(maxTime)) {
                spec = spec.and(ProgramationSpecification.filterByActivityTimeRange(minTime, maxTime));
            } else {
                throw new IllegalArgumentException("La hora mínima debe ser anterior a la fecha máxima.");
            }
        }


        if (criteria.speciesId() > 0) {
            spec = spec.and(ProgramationSpecification.filterByAnimalSpecies(criteria.speciesId()));
        }

        // Crear un objeto Pageable usando pageNumber y itemsPerPage
        Pageable pageable = PageRequest.of(criteria.pageNumber(), criteria.itemsPerPage(), Sort.by("id"));
        
        // Obtener la lista de programationes según la especificación y la paginación
        Page<Programation> programationsPage = programationRepository.findAll(spec, pageable);
        
        // Convertir a DTOs
        return programationsPage.map(this::mapToOutputDTO);
    }



    public void deleteProgramation(int id) {
        Programation programation = programationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Programacion no encontrado"));

        try {
            programationRepository.delete(programation);
        } catch (DataIntegrityViolationException e) {
            throw new ConstraintViolationException("No se puede eliminar el programacion porque tiene dependencias relacionadas.");
        }
    }

    public ProgramationOutputDTO findById(int id) {
        return mapToOutputDTO(programationRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Programacion no encontrado")));
    }

    public ProgramationOutputDTO updateProgramation(int id, ProgramationInputDTO updatedProgramation) {
        // Verificar si el programacion existe
        Programation programation = programationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Programacion no encontrado"));
    
        Animal animal = animalRepository.findById(updatedProgramation.animalId())
                .orElseThrow(() -> new ResourceNotFoundException("Animal no encontrado"));

        Activity activity = activityRepository.findById(updatedProgramation.activityId())
                .orElseThrow(() -> new ResourceNotFoundException("Actividad no encontrada"));
    
        // Actualizar los valores del programacion
        programation.setActivity(activity);
        programation.setAnimal(animal);
    
        try {
            // Guardar y devolver el programacion actualizado
            return mapToOutputDTO(programationRepository.save(programation));
        } catch (Exception e) {
            // Captura la excepción lanzada por el trigger
            throw new InvalidInputException(globalExceptionHandler.extractErrorMessage(e.getMessage()));
        }
        
    }
}
