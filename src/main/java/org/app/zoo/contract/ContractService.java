package org.app.zoo.contract;

import org.app.zoo.contract.dto.in.ContractInputDTO;
import org.app.zoo.contract.dto.out.ContractOutputDTO;
import org.app.zoo.provider.Provider;
import org.app.zoo.provider.ProviderRepository;
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
@Schema(description = "Contract service who has the implementations of crud functions and more")
public class ContractService {
    private final ContractRepository contractRepository;
    private final ProviderRepository providerRepository;
    private final GlobalExceptionHandler globalExceptionHandler;

    public ContractService(ContractRepository contractRepository, ProviderRepository providerRepository, GlobalExceptionHandler globalExceptionHandler) {
        this.contractRepository = contractRepository;
        this.providerRepository = providerRepository;
        this.globalExceptionHandler = globalExceptionHandler;
    }

    public ContractOutputDTO createContract(ContractInputDTO contractInputDTO) {
        Provider provider = providerRepository.findById(contractInputDTO.providerId())
                .orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado"));

        Contract contract = new Contract();
        contract.setProvider(provider);

        // Assign an startingDate if any
        if (contractInputDTO.startingDate() != null) {
            contract.setStartingDate(contractInputDTO.startingDate());
        }else{
            throw new InvalidInputException("La fecha de inicio no puede estar vacia");
        }

        // Assign an endingDate if any
        if (contractInputDTO.endingDate() != null) {
            contract.setEndingDate(contractInputDTO.endingDate());
        }else{
            throw new InvalidInputException("La fecha de terminacion no puede estar vacia");
        }

        // Assign a conciliationDate if any
        if (contractInputDTO.conciliationDate() != null) {
            contract.setConciliationDate(contractInputDTO.conciliationDate());
        }else{
            throw new InvalidInputException("La fecha de conciliacion no puede estar vacia");
        }

        if (contractInputDTO.description() == null || contractInputDTO.description().trim().isEmpty()){
            throw new InvalidInputException("La descripcion no puede estar vacia");
        }
        
        contract.setDescription(contractInputDTO.description());

        if(contractInputDTO.basePrice() < 0){
            throw new InvalidInputException("El precio base debe ser mayor que 0");
        }
        
        contract.setBasePrice(contractInputDTO.basePrice());

        try {
            Contract savedContract = contractRepository.save(contract);
            return mapToOutputDTO(savedContract);
        } catch (Exception e) {
            // Captura la excepción lanzada por el trigger
            throw new InvalidInputException(globalExceptionHandler.extractErrorMessage(e.getMessage()));
        } 
    }

    public Page<ContractOutputDTO> getAllContract(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("id")); // Crear un objeto Pageable
    
        // Obtener la lista de contractes según la paginación
        Page<Contract> contractsPage = contractRepository.findAll(pageable);
    
        // Convertir a DTOs
        return contractsPage.map(this::mapToOutputDTO);
    }

    private ContractOutputDTO mapToOutputDTO(Contract contract) {
        ContractOutputDTO contractOutputDTO = new ContractOutputDTO(
            contract.getId(),
            contract.getProvider().getId(),
            contract.getStartingDate(),
            contract.getEndingDate(),
            contract.getConciliationDate(),
            contract.getDescription(),
            contract.getBasePrice()
        );
        return contractOutputDTO;
    }

    public Page<ContractOutputDTO> searchContract(ContractSearchCriteria criteria) {
        // Todo esta validado en contractSpecification

        Specification<Contract> spec = Specification.where(null);

        // Aplicar cada filtro si es válido
        if (criteria.searchField() != null && !criteria.searchField().isEmpty()){
            spec = spec.and(ContractSpecification.filterBySearchField(criteria.searchField()));
        }

        if (criteria.providerTypeId() > 0) {
            spec = spec.and(ContractSpecification.filterByProviderTypeId(criteria.providerTypeId()));
        }

        if (criteria.contractState() > 0) {
            if(criteria.contractState() == 1){ // activos
                spec = spec.and(ContractSpecification.filterByActiveContracts());
            }
            else if(criteria.contractState() == 2){ // no activos
                spec = spec.and(ContractSpecification.filterByNoActiveContracts());
            }
            else{ // futuros
                spec = spec.and(ContractSpecification.filterByFutureContracts());
            }
        }

        if (criteria.minBasePrice() > 0 && criteria.maxBasePrice() > 0 && criteria.minBasePrice() <= criteria.maxBasePrice()) {
            spec = spec.and(ContractSpecification.filterByBasePrice(criteria.minBasePrice(), criteria.maxBasePrice()));
        }

        // Crear un objeto Pageable usando pageNumber y itemsPerPage
        Pageable pageable = PageRequest.of(criteria.pageNumber(), criteria.itemsPerPage(), Sort.by("id"));
        
        // Obtener la lista de contractes según la especificación y la paginación
        Page<Contract> contractsPage = contractRepository.findAll(spec, pageable);
        
        // Convertir a DTOs
        return contractsPage.map(this::mapToOutputDTO);
    }



    public void deleteContract(int id) {
        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contrato no encontrado"));

        try {
            contractRepository.delete(contract);
        } catch (DataIntegrityViolationException e) {
            throw new ConstraintViolationException("No se puede eliminar el contrato porque tiene dependencias relacionadas.");
        }
    }

    public ContractOutputDTO findById(int id) {
        return mapToOutputDTO(contractRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Contrato no encontrado")));
    }

    public ContractOutputDTO updateContract(int id, ContractInputDTO updatedContract) {
        // Verificar si el contrato existe
        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contrato no encontrado"));
    
        Provider provider = providerRepository.findById(updatedContract.providerId())
                .orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado"));
    
        // Validar fechas
        if (updatedContract.startingDate() == null) {
            throw new InvalidInputException("La fecha de inicio no puede estar vacia");
        }
        if (updatedContract.endingDate() == null) {
            throw new InvalidInputException("La fecha de terminacion no puede estar vacia");
        }
        if (updatedContract.conciliationDate() == null) {
            throw new InvalidInputException("La fecha de conciliacion no puede estar vacia");
        }
    
        // Validar descripción
        if (updatedContract.description() == null || updatedContract.description().isEmpty()) {
            throw new InvalidInputException("La descripcion del contrato no puede estar vacío");
        }
    
        // Validar precio base
        if (updatedContract.basePrice() < 0) {
            throw new InvalidInputException("El precio base no puede ser negativo");
        }
    
        // Verificar si ya existe un contrato con las mismas características
        boolean exists = contractRepository.existsByProviderIdAndStartingDateAndEndingDateAndConciliationDateAndDescriptionAndBasePrice(
                updatedContract.providerId(),
                updatedContract.startingDate(),
                updatedContract.endingDate(),
                updatedContract.conciliationDate(),
                updatedContract.description(),
                updatedContract.basePrice()
        );
    
        if (exists) {
            throw new ResourceAlreadyExistsException("Ya existe un contrato con las mismas características.");
        }
    
        // Actualizar los valores del contrato
        contract.setProvider(provider);
        contract.setStartingDate(updatedContract.startingDate());
        contract.setEndingDate(updatedContract.endingDate());
        contract.setConciliationDate(updatedContract.conciliationDate());
        contract.setDescription(updatedContract.description());
        contract.setBasePrice(updatedContract.basePrice());
    
        try {
            // Guardar y devolver el contrato actualizado
            return mapToOutputDTO(contractRepository.save(contract));
        } catch (Exception e) {
            // Captura la excepción lanzada por el trigger
            throw new InvalidInputException(globalExceptionHandler.extractErrorMessage(e.getMessage()));
        }
        
    }
    
}
