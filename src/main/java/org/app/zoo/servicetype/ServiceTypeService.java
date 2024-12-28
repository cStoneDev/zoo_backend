package org.app.zoo.servicetype;

import org.app.zoo.config.errorHandling.ConstraintViolationException;
import org.app.zoo.config.errorHandling.InvalidInputException;
import org.app.zoo.config.errorHandling.ResourceAlreadyExistsException;
import org.app.zoo.config.errorHandling.ResourceNotFoundException;
import org.app.zoo.servicetype.dto.in.ServiceTypeInputDTO;
import org.app.zoo.servicetype.dto.out.ServiceTypeOutputDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import io.swagger.v3.oas.annotations.media.Schema;

@Service
@Schema(description = "ServiceType service with de impl of CRUD functions")
public class ServiceTypeService {
    
    private final ServiceTypeRepository serviceTypeRepository;

    public ServiceTypeService(ServiceTypeRepository serviceTypeRepository){
        this.serviceTypeRepository = serviceTypeRepository;
    }


    public ServiceTypeOutputDTO createServiceType(ServiceTypeInputDTO ServiceTypeInputDTO){
        ServiceType serviceType = new ServiceType(ServiceTypeInputDTO.name());
        ServiceType savedServiceType = serviceTypeRepository.save(serviceType);
        return mapToOutputDTO(savedServiceType);
    }

    private ServiceTypeOutputDTO mapToOutputDTO(ServiceType serviceType){
        return new ServiceTypeOutputDTO(
            serviceType.getId(),
            serviceType.getName());
    }

    public Page<ServiceTypeOutputDTO> getAllServiceTypes(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("id")); // Crear un objeto Pageable
    
        // Obtener la lista de speciees según la paginación
        Page<ServiceType> serviceTypePage = serviceTypeRepository.findAll(pageable);
    
        // Convertir a DTOs
        return serviceTypePage.map(this::mapToOutputDTO);
    }

    public Page<ServiceTypeOutputDTO> searchServiceType(ServiceTypeSearchCriteria criteria) {
        // Todo esta validado en serviceTypeSpecification

        Specification<ServiceType> spec = Specification.where(null);

        // Aplicar cada filtro si es válido
        if (criteria.searchField() != null && !criteria.searchField().isEmpty()){
            spec = spec.and(ServiceTypeSpecification.filterBySearchField(criteria.searchField()));
        }
        // Crear un objeto Pageable usando pageNumber y itemsPerPage
        Pageable pageable = PageRequest.of(criteria.pageNumber(), criteria.itemsPerPage(), Sort.by("id"));
        
        // Obtener la lista de speciees según la especificación y la paginación
        Page<ServiceType> serviceTypePage = serviceTypeRepository.findAll(spec, pageable);
        
        // Convertir a DTOs
        return serviceTypePage.map(this::mapToOutputDTO);
    }

    public void deleteServiceType(int id) {
        // Verificar si el tipo de servicio existe
        ServiceType serviceType = serviceTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de servicio no encontrada"));

        try {
            serviceTypeRepository.delete(serviceType);
        } catch (DataIntegrityViolationException e) {
            throw new ConstraintViolationException("No se puede eliminar el tipo de servicio porque tiene dependencias relacionadas.");
        }
    }

    public ServiceTypeOutputDTO findById(int id) {
        return mapToOutputDTO(serviceTypeRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Tipo de servicio no encontrada")));
    }

    public ServiceTypeOutputDTO updateServiceType(int id, ServiceTypeInputDTO updatedServiceType) {
        // Verificar si el tipo de servicio existe
        ServiceType serviceType = serviceTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de servicio no encontrada"));

        // Validar que el nombre no esté vacío
        if (updatedServiceType.name() == null || updatedServiceType.name().isEmpty()) {
            throw new InvalidInputException("El nombre de el tipo de servicio no puede estar vacío");
        }

        // Validar que no exista otra especie con el mismo nombre
        boolean exists = serviceTypeRepository.existsByNameAndIdNot(updatedServiceType.name(), id);
        if (exists) {
            throw new ResourceAlreadyExistsException("Ya existe una especie con el nombre: " + updatedServiceType.name());
        }

        // Actualizar los valores de el tipo de servicio
        serviceType.setName(updatedServiceType.name());
        // (Actualizar otros campos según corresponda)

        // Guardar y devolver el tipo de servicio actualizada
        serviceTypeRepository.save(serviceType);
        return mapToOutputDTO(serviceType);
    }
}
