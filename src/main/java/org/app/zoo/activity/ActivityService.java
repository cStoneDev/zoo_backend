package org.app.zoo.activity;

import org.app.zoo.activity.dto.in.ActivityInputDTO;
import org.app.zoo.activity.dto.out.ActivityOutputDTO;
import org.app.zoo.config.GlobalExceptionHandler;
import org.app.zoo.config.errorHandling.ConstraintViolationException;
import org.app.zoo.config.errorHandling.InvalidInputException;
import org.app.zoo.config.errorHandling.ResourceAlreadyExistsException;
import org.app.zoo.config.errorHandling.ResourceNotFoundException;
import org.app.zoo.contract.Contract;
import org.app.zoo.contract.ContractRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import io.swagger.v3.oas.annotations.media.Schema;

@Service
@Schema(description = "Activity service who has the implementations of crud functions and more")
public class ActivityService {
    private final ContractRepository contractRepository;
    private final ActivityRepository activityRepository;
    private final GlobalExceptionHandler globalExceptionHandler;

    public ActivityService(ContractRepository contractRepository, ActivityRepository activityRepository,
            GlobalExceptionHandler globalExceptionHandler) {
        this.contractRepository = contractRepository;
        this.activityRepository = activityRepository;
        this.globalExceptionHandler = globalExceptionHandler;
    }

    public ActivityOutputDTO createActivity(ActivityInputDTO activityInputDTO){
        Contract contract = contractRepository.findById(activityInputDTO.contractId())
                .orElseThrow(() -> new ResourceNotFoundException("Contrato no encontrado")
        );

        Activity activity = new Activity();
        activity.setContract(contract);

        if (activityInputDTO.date() != null) {
            activity.setDate(activityInputDTO.date());
        }else{
            throw new InvalidInputException("La fecha no puede estar vacía");
        }

        if (activityInputDTO.description() == null || activityInputDTO.description().trim().isEmpty()){
            throw new InvalidInputException("La descripción no puede estar vacía");
        }

        activity.setDescription(activityInputDTO.description());

        if (activityInputDTO.time() == null){
            throw new InvalidInputException("La hora no puede estar vacía");
        }

        activity.setTime(activityInputDTO.time());

        try {
            Activity savedActivity = activityRepository.save(activity);
            return mapToOutputDTO(savedActivity);
        } catch (Exception e) {
            // Captura la excepción lanzada por el trigger
            throw new InvalidInputException(globalExceptionHandler.extractErrorMessage(e.getMessage()));
        } 
    }

    public Page<ActivityOutputDTO> getAllActivity(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("id"));
        Page<Activity> activitiesPage = activityRepository.findAll(pageable);
        return activitiesPage.map(this::mapToOutputDTO);
    }

    public ActivityOutputDTO updateActivity(int id, ActivityInputDTO updatedActivity){
        Contract contract = contractRepository.findById(updatedActivity.contractId())
                .orElseThrow(() -> new ResourceNotFoundException("Contrato no encontrado")
        );
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Actividad no encontrada")
        );
        if (updatedActivity.date() == null) {
            throw new InvalidInputException("La fecha no puede estar vacía");
        }
        if (updatedActivity.description() == null || updatedActivity.description().trim().isEmpty()){
            throw new InvalidInputException("La descripción no puede estar vacía");
        }
        if (updatedActivity.time() == null){
            throw new InvalidInputException("La hora no puede estar vacía");
        }

        boolean exists = activityRepository.existsByContractIdAndDateAndTimeAndDescription(
            updatedActivity.contractId(), 
            updatedActivity.date(), 
            updatedActivity.time(), 
            updatedActivity.description()
        );

        if (exists) {
            throw new ResourceAlreadyExistsException("Ya existe una actividad con las mismas características.");
        }

        activity.setContract(contract);
        activity.setDescription(updatedActivity.description());
        activity.setDate(updatedActivity.date());
        activity.setTime(updatedActivity.time());

        try {
            return mapToOutputDTO(activityRepository.save(activity));
        } catch (Exception e) {
            // Captura la excepción lanzada por el trigger
            throw new InvalidInputException(globalExceptionHandler.extractErrorMessage(e.getMessage()));
        }
    }

    public ActivityOutputDTO findById(int id) {
        return mapToOutputDTO(activityRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Actividad no encontrada")));
    }

    public Page<ActivityOutputDTO> searchActivity(ActivitySearchCriteria criteria){
        Specification<Activity> spec = Specification.where(null);

        // Aplicar cada filtro si es válido
        if (criteria.searchField() != null && !criteria.searchField().trim().isEmpty()){
            spec = spec.and(ActivitySpecification.filterBySearchField(criteria.searchField()));
        }
        if (criteria.contractId() > 0) {
            spec = spec.and(ActivitySpecification.filterByContractId(criteria.contractId()));
        }
        if (criteria.providerTypeId() > 0) {
            spec = spec.and(ActivitySpecification.filterByProviderTypeId(criteria.providerTypeId()));
        }

        if (criteria.activitiesState() > 0) {
            if(criteria.activitiesState() == 1){ // activos
                spec = spec.and(ActivitySpecification.filterByActiveActivities());
            }
            else if(criteria.activitiesState() == 2){ // no activos
                spec = spec.and(ActivitySpecification.filterByNoActiveActivities());
            }
        }

        // Crear un objeto Pageable usando pageNumber y itemsPerPage
        Pageable pageable = PageRequest.of(criteria.pageNumber(), criteria.itemsPerPage(), Sort.by("id"));
        
        // Obtener la lista de contractes según la especificación y la paginación
        Page<Activity> activitiesPage = activityRepository.findAll(spec, pageable);
        
        // Convertir a DTOs
        return activitiesPage.map(this::mapToOutputDTO);

    }

    public void deleteActivity(int id) {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Actividad no encontrada"));
        try {
            activityRepository.delete(activity);
        } catch (DataIntegrityViolationException e) {
            throw new ConstraintViolationException("No se puede eliminar la actividad porque tiene dependencias relacionadas.");
        }
    }

    private ActivityOutputDTO mapToOutputDTO(Activity activity){
        ActivityOutputDTO activityOutputDTO = new ActivityOutputDTO(
            activity.getId(), 
            activity.getContract().getId(), 
            activity.getDate(), 
            activity.getTime(), 
            activity.getDescription()
        );
        return activityOutputDTO;
    }
}
