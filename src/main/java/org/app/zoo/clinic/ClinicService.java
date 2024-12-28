package org.app.zoo.clinic;

import org.app.zoo.clinic.dto.in.ClinicInputDTO;
import org.app.zoo.clinic.dto.out.ClinicOutputDTO;
import org.app.zoo.config.GlobalExceptionHandler;
import org.app.zoo.config.errorHandling.ConstraintViolationException;
import org.app.zoo.config.errorHandling.InvalidInputException;
import org.app.zoo.config.errorHandling.ResourceAlreadyExistsException;
import org.app.zoo.config.errorHandling.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import io.swagger.v3.oas.annotations.media.Schema;

@Service
@Schema(description = "Clinic service who has the implementations of crud functions and more")
public class ClinicService {

    private final ClinicRepository clinicRepository;
    private final GlobalExceptionHandler globalExceptionHandler;

    public ClinicService(GlobalExceptionHandler globalExceptionHandler, ClinicRepository clinicRepository) {
        this.clinicRepository = clinicRepository;
        this.globalExceptionHandler = globalExceptionHandler;
    }

    public ClinicOutputDTO createClinic(ClinicInputDTO clinic) {
        if (clinic.name() == null || clinic.name().isEmpty()) {
            throw new InvalidInputException("El nombre de la clinica no puede estar vacío");
        }
        Clinic clinicSave = new Clinic(clinic.name());

        try {
            clinicRepository.save(clinicSave);
            return mapToOutputDTO(clinicSave);
        } catch (Exception e) {
            throw new InvalidInputException(globalExceptionHandler.extractErrorMessage(e.getMessage()));
        }

    }

    public void deleteClinic(int id) {
        // Verificar si la clinica existe
        Clinic clinic = clinicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Clinica no encontrada"));

        try {
            clinicRepository.delete(clinic);
        } catch (DataIntegrityViolationException e) {
            throw new ConstraintViolationException(
                    "No se puede eliminar la clinica porque tiene dependencias relacionadas.");
        }
    }

    public ClinicOutputDTO findById(int id) {
        return mapToOutputDTO(clinicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Clinica no encontrada")));
    }

    public ClinicOutputDTO updateClinic(int id, ClinicInputDTO updatedClinic) {
        // Verificar si la clinica existe
        Clinic clinic = clinicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Clinica no encontrada"));

        // Validar que el nombre no esté vacío
        if (updatedClinic.name() == null || updatedClinic.name().isEmpty()) {
            throw new InvalidInputException("El nombre de la clinica no puede estar vacío");
        }

        // Validar que no exista otra clinica con el mismo nombre
        boolean exists = clinicRepository.existsByNameAndIdNot(updatedClinic.name(), id);
        if (exists) {
            throw new ResourceAlreadyExistsException("Ya existe una clinica con el nombre: " + updatedClinic.name());
        }

        // Actualizar los valores de la clinica
        clinic.setName(updatedClinic.name());
        // (Actualizar otros campos según corresponda)

        // Guardar y devolver la clinica actualizada

        try {
            clinicRepository.save(clinic);
            return mapToOutputDTO(clinic);
        } catch (Exception e) {
            throw new InvalidInputException(globalExceptionHandler.extractErrorMessage(e.getMessage()));
        }

    }

    public Page<ClinicOutputDTO> getAllClinic(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("id")); // Crear un objeto Pageable

        // Obtener la lista de speciees según la paginación
        Page<Clinic> clinicPage = clinicRepository.findAll(pageable);

        // Convertir a DTOs
        return clinicPage.map(this::mapToOutputDTO);
    }

    private ClinicOutputDTO mapToOutputDTO(Clinic clinic) {
        ClinicOutputDTO clinicOutputDTO = new ClinicOutputDTO(
                clinic.getId(),
                clinic.getName());
        return clinicOutputDTO;
    }

    public Page<ClinicOutputDTO> searchClinic(ClinicSearchCriteria criteria) {
        // Todo esta validado en clinicSpecification

        Specification<Clinic> spec = Specification.where(null);

        // Aplicar cada filtro si es válido
        if (criteria.searchField() != null && !criteria.searchField().isEmpty()) {
            spec = spec.and(ClinicSpecification.filterBySearchField(criteria.searchField()));
        }
        // Crear un objeto Pageable usando pageNumber y itemsPerPage
        Pageable pageable = PageRequest.of(criteria.pageNumber(), criteria.itemsPerPage(), Sort.by("id"));

        // Obtener la lista de speciees según la especificación y la paginación
        Page<Clinic> clinicPage = clinicRepository.findAll(spec, pageable);

        // Convertir a DTOs
        return clinicPage.map(this::mapToOutputDTO);
    }

}