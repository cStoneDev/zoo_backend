package org.app.zoo.speciality;

import org.app.zoo.config.GlobalExceptionHandler;
import org.app.zoo.config.errorHandling.ConstraintViolationException;
import org.app.zoo.config.errorHandling.InvalidInputException;
import org.app.zoo.config.errorHandling.ResourceAlreadyExistsException;
import org.app.zoo.config.errorHandling.ResourceNotFoundException;
import org.app.zoo.speciality.dto.in.SpecialityInputDTO;
import org.app.zoo.speciality.dto.out.SpecialityOutputDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import io.swagger.v3.oas.annotations.media.Schema;

@Service
@Schema(description = "Speciality service who has the implementations of crud functions and more")
public class SpecialityService {

    private final SpecialityRepository specialityRepository;
    private final GlobalExceptionHandler globalExceptionHandler;

    public SpecialityService(GlobalExceptionHandler globalExceptionHandler, SpecialityRepository specialityRepository) {
        this.globalExceptionHandler = globalExceptionHandler;
        this.specialityRepository = specialityRepository;
    }

    public SpecialityOutputDTO createSpeciality(SpecialityInputDTO speciality) {
        if (speciality.name() == null || speciality.name().isEmpty()) {
            throw new InvalidInputException("El nombre de la especialidad no puede estar vacío");
        }
        Speciality specialitySave = new Speciality(speciality.name());

        try {
            specialityRepository.save(specialitySave);
            return mapToOutputDTO(specialitySave);
        } catch (Exception e) {
            throw new InvalidInputException(globalExceptionHandler.extractErrorMessage(e.getMessage()));
        }

    }

    public void deleteSpeciality(int id) {
        // Verificar si la especialidad existe
        Speciality speciality = specialityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Especialidad no encontrada"));

        try {
            specialityRepository.delete(speciality);
        } catch (DataIntegrityViolationException e) {
            throw new ConstraintViolationException(
                    "No se puede eliminar la especialidad porque tiene dependencias relacionadas.");
        }
    }

    public SpecialityOutputDTO findById(int id) {
        return mapToOutputDTO(specialityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Especialidad no encontrada")));
    }

    public SpecialityOutputDTO updateSpeciality(int id, SpecialityInputDTO updatedSpeciality) {
        // Verificar si la especialidad existe
        Speciality speciality = specialityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Especialidad no encontrada"));

        // Validar que el nombre no esté vacío
        if (updatedSpeciality.name() == null || updatedSpeciality.name().isEmpty()) {
            throw new InvalidInputException("El nombre de la especialidad no puede estar vacío");
        }

        // Validar que no exista otra especialidad con el mismo nombre
        boolean exists = specialityRepository.existsByNameAndIdNot(updatedSpeciality.name(), id);
        if (exists) {
            throw new ResourceAlreadyExistsException(
                    "Ya existe una especialidad con el nombre: " + updatedSpeciality.name());
        }

        // Actualizar los valores de la especialidad
        speciality.setName(updatedSpeciality.name());
        // (Actualizar otros campos según corresponda)

        // Guardar y devolver la especialidad actualizada

        try {
            specialityRepository.save(speciality);
            return mapToOutputDTO(speciality);
        } catch (Exception e) {
            throw new InvalidInputException(globalExceptionHandler.extractErrorMessage(e.getMessage()));
        }

    }

    public Page<SpecialityOutputDTO> getAllSpeciality(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("id")); // Crear un objeto Pageable

        // Obtener la lista de speciees según la paginación
        Page<Speciality> specialityPage = specialityRepository.findAll(pageable);

        // Convertir a DTOs
        return specialityPage.map(this::mapToOutputDTO);
    }

    private SpecialityOutputDTO mapToOutputDTO(Speciality speciality) {
        SpecialityOutputDTO specialityOutputDTO = new SpecialityOutputDTO(
                speciality.getId(),
                speciality.getName());
        return specialityOutputDTO;
    }

    public Page<SpecialityOutputDTO> searchSpeciality(SpecialitySearchCriteria criteria) {
        // Todo esta validado en specialitySpecification

        Specification<Speciality> spec = Specification.where(null);

        // Aplicar cada filtro si es válido
        if (criteria.searchField() != null && !criteria.searchField().isEmpty()) {
            spec = spec.and(SpecialitySpecification.filterBySearchField(criteria.searchField()));
        }
        // Crear un objeto Pageable usando pageNumber y itemsPerPage
        Pageable pageable = PageRequest.of(criteria.pageNumber(), criteria.itemsPerPage(), Sort.by("id"));

        // Obtener la lista de speciees según la especificación y la paginación
        Page<Speciality> specialityPage = specialityRepository.findAll(spec, pageable);

        // Convertir a DTOs
        return specialityPage.map(this::mapToOutputDTO);
    }
}