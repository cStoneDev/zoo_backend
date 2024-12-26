package org.app.zoo.providertype;

import org.app.zoo.config.errorHandling.ConstraintViolationException;
import org.app.zoo.config.errorHandling.InvalidInputException;
import org.app.zoo.config.errorHandling.ResourceAlreadyExistsException;
import org.app.zoo.config.errorHandling.ResourceNotFoundException;
import org.app.zoo.providertype.dto.in.ProviderTypeInputDTO;
import org.app.zoo.providertype.dto.out.ProviderTypeOutputDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import io.swagger.v3.oas.annotations.media.Schema;

@Service
@Schema(description = "ProviderType service with de impl of CRUD functions")
public class ProviderTypeService {
    
    private final ProviderTypeRepository providerTypeRepository;

    public ProviderTypeService(ProviderTypeRepository providerTypeRepository){
        this.providerTypeRepository = providerTypeRepository;
    }


    public ProviderTypeOutputDTO createProviderType(ProviderTypeInputDTO providerTypeInputDTO){
        ProviderType providerType = new ProviderType(providerTypeInputDTO.name());
        ProviderType savedProviderType = providerTypeRepository.save(providerType);
        return mapToOutputDTO(savedProviderType);
    }

    private ProviderTypeOutputDTO mapToOutputDTO(ProviderType providerType){
        return new ProviderTypeOutputDTO(
            providerType.getId(),
            providerType.getName());
    }

    public Page<ProviderTypeOutputDTO> getAllProviderTypes(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("id")); // Crear un objeto Pageable
    
        // Obtener la lista de speciees según la paginación
        Page<ProviderType> providerTypePage = providerTypeRepository.findAll(pageable);
    
        // Convertir a DTOs
        return providerTypePage.map(this::mapToOutputDTO);
    }

    public Page<ProviderTypeOutputDTO> searchProviderType(ProviderTypeSearchCriteria criteria) {
        // Todo esta validado en providerTypeSpecification

        Specification<ProviderType> spec = Specification.where(null);

        // Aplicar cada filtro si es válido
        if (criteria.searchField() != null && !criteria.searchField().isEmpty()){
            spec = spec.and(ProviderTypeSpecification.filterBySearchField(criteria.searchField()));
        }
        // Crear un objeto Pageable usando pageNumber y itemsPerPage
        Pageable pageable = PageRequest.of(criteria.pageNumber(), criteria.itemsPerPage(), Sort.by("id"));
        
        // Obtener la lista de speciees según la especificación y la paginación
        Page<ProviderType> providerTypePage = providerTypeRepository.findAll(spec, pageable);
        
        // Convertir a DTOs
        return providerTypePage.map(this::mapToOutputDTO);
    }

    public void deleteProviderType(int id) {
        // Verificar si el tipo de proveedor existe
        ProviderType providerType = providerTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de proveedor no encontrada"));

        try {
            providerTypeRepository.delete(providerType);
        } catch (DataIntegrityViolationException e) {
            throw new ConstraintViolationException("No se puede eliminar el tipo de proveedor porque tiene dependencias relacionadas.");
        }
    }

    public ProviderTypeOutputDTO findById(int id) {
        return mapToOutputDTO(providerTypeRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Tipo de proveedor no encontrada")));
    }

    public ProviderTypeOutputDTO updateProviderType(int id, ProviderTypeInputDTO updatedProviderType) {
        // Verificar si el tipo de proveedor existe
        ProviderType providerType = providerTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de proveedor no encontrada"));

        // Validar que el nombre no esté vacío
        if (updatedProviderType.name() == null || updatedProviderType.name().isEmpty()) {
            throw new InvalidInputException("El nombre de el tipo de proveedor no puede estar vacío");
        }

        // Validar que no exista otra especie con el mismo nombre
        boolean exists = providerTypeRepository.existsByNameAndIdNot(updatedProviderType.name(), id);
        if (exists) {
            throw new ResourceAlreadyExistsException("Ya existe una especie con el nombre: " + updatedProviderType.name());
        }

        // Actualizar los valores de el tipo de proveedor
        providerType.setName(updatedProviderType.name());
        // (Actualizar otros campos según corresponda)

        // Guardar y devolver el tipo de proveedor actualizada
        providerTypeRepository.save(providerType);
        return mapToOutputDTO(providerType);
    }
}
