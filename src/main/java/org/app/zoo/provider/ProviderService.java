package org.app.zoo.provider;

import org.app.zoo.clinic.Clinic;
import org.app.zoo.clinic.ClinicRepository;
import org.app.zoo.config.GlobalExceptionHandler;
import org.app.zoo.config.errorHandling.ConstraintViolationException;
import org.app.zoo.config.errorHandling.InvalidInputException;
import org.app.zoo.config.errorHandling.ResourceAlreadyExistsException;
import org.app.zoo.config.errorHandling.ResourceNotFoundException;
import org.app.zoo.provider.dto.in.ProviderInputDTO;
import org.app.zoo.provider.dto.out.ProviderOutputDTO;
import org.app.zoo.provider.dto.out.ProviderResponseDTO;
import org.app.zoo.provider.dto.out.ProviderWithVeterinarianOutputDTO;
import org.app.zoo.providertype.ProviderType;
import org.app.zoo.providertype.ProviderTypeRepository;
import org.app.zoo.province.Province;
import org.app.zoo.province.ProvinceRepository;
import org.app.zoo.servicetype.ServiceTypeRepository;
import org.app.zoo.speciality.Speciality;
import org.app.zoo.speciality.SpecialityRepository;
import org.app.zoo.servicetype.ServiceType;
import org.app.zoo.veterinarian.Veterinarian;
import org.app.zoo.veterinarian.VeterinarianRepository;
import org.app.zoo.veterinarian.VeterinarianService;
import org.app.zoo.veterinarian.dto.out.VeterinarianOutputDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import io.swagger.v3.oas.annotations.media.Schema;

@Service
@Schema(description = "Provider service who has the implementations of crud functions and more")
public class ProviderService {

    private ProviderRepository providerRepository;
    private ProvinceRepository provinceRepository;
    private ProviderTypeRepository providerTypeRepository;
    private ServiceTypeRepository serviceTypeRepository;
    private VeterinarianRepository veterinarianRepository;
    private VeterinarianService veterinarianService;
    private ClinicRepository clinicRepository;
    private SpecialityRepository specialityRepository;
    private final GlobalExceptionHandler globalExceptionHandler;

    protected final int veterinarianType = 1;

    public ProviderService(GlobalExceptionHandler globalExceptionHandler,
            ProviderRepository providerRepository,
            ProvinceRepository provinceRepository,
            ProviderTypeRepository providerTypeRepository,
            ServiceTypeRepository serviceTypeRepository,
            VeterinarianRepository veterinarianRepository,
            VeterinarianService veterinarianService,
            ClinicRepository clinicRepository,
            SpecialityRepository specialityRepository) {
        this.globalExceptionHandler = globalExceptionHandler;
        this.providerRepository = providerRepository;
        this.provinceRepository = provinceRepository;
        this.providerTypeRepository = providerTypeRepository;
        this.serviceTypeRepository = serviceTypeRepository;
        this.veterinarianRepository = veterinarianRepository;
        this.veterinarianService = veterinarianService;
        this.clinicRepository = clinicRepository;
        this.specialityRepository = specialityRepository;
    }

    private ProviderResponseDTO assignProvider(Provider provider, ProviderInputDTO providerInputDTO) {
        Province province = provinceRepository.findById(providerInputDTO.provinceId())
                .orElseThrow(() -> new ResourceNotFoundException("Provincia no encontrada"));

        ProviderType providerType = providerTypeRepository.findById(providerInputDTO.providerTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado"));

        ServiceType serviceType = serviceTypeRepository.findById(providerInputDTO.serviceTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Servicio no encontrado"));

        provider.setName(providerInputDTO.name());
        provider.setAddress(providerInputDTO.address());
        provider.setEmail(providerInputDTO.email());
        provider.setPhone(providerInputDTO.phone());
        provider.setResponsibleName(providerInputDTO.responsibleName());
        provider.setProviderType(providerType);
        provider.setProvince(province);
        provider.setServiceType(serviceType);
        Provider providerOut = new Provider();
        try {
            providerOut = providerRepository.save(provider);
        } catch (Exception e) {
            throw new InvalidInputException(globalExceptionHandler.extractErrorMessage(e.getMessage()));
        }

        if (providerInputDTO.providerTypeId() == veterinarianType) {
            Clinic clinic = clinicRepository.findById(providerInputDTO.clinicId())
                    .orElseThrow(() -> new ResourceNotFoundException("Clinica no encontrada"));

            Speciality speciality = specialityRepository.findById(providerInputDTO.specialityId())
                    .orElseThrow(() -> new ResourceNotFoundException("Especialidad no encontrada"));

            Veterinarian veterinarian = new Veterinarian();

            veterinarian.setProvider(providerOut);
            veterinarian.setClinic(clinic);
            veterinarian.setFax(providerInputDTO.fax());
            veterinarian.setSpeciality(speciality);
            veterinarian.setCityDistance(providerInputDTO.cityDistance());

            try {
                veterinarianRepository.save(veterinarian);
            } catch (Exception e) {
                throw new InvalidInputException(globalExceptionHandler.extractErrorMessage(e.getMessage()));
            }

        }
        return mapToOutputDTO(providerOut);
    }

    public ProviderResponseDTO createProvider(ProviderInputDTO providerInputDTO) {
        Provider provider = new Provider();
        ProviderResponseDTO response = assignProvider(provider, providerInputDTO);
        return response;
    }

    public Page<ProviderResponseDTO> getAllProviders(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("id"));
        return providerRepository.findAll(pageable).map(this::mapToOutputDTO);
    }

    public ProviderResponseDTO findById(int id) {
        return providerRepository.findById(id)
                .map(this::mapToOutputDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado con ID: " + id));
    }

    public void deleteProvider(int id) {
        Provider provider = providerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado con ID: " + id));
        try {
            providerRepository.delete(provider);
        } catch (DataIntegrityViolationException e) {
            throw new ConstraintViolationException("No se puede eliminar el proveedor porque tiene dependencias.");
        }
    }

    public ProviderResponseDTO updateProvider(int id, ProviderInputDTO updatedInput) {
        Provider provider = providerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado con ID: " + id));

        boolean good = validateProviderInput(updatedInput);

        if (good) {
            throw new ResourceAlreadyExistsException("Ya existe un proveedor con las mismas características.");
        }
        ProviderResponseDTO response = assignProvider(provider, updatedInput);
        return response;
    }

    private boolean validateProviderInput(ProviderInputDTO input) {
        if (input.name() == null || input.name().length() < 3 || input.name().isEmpty()) {
            throw new InvalidInputException(
                    "El nombre del proveedor debe tener al menos 3 caracteres y no estar vacío");
        }
        if (input.phone() == null || input.phone().length() != 8 || input.phone().isEmpty()) {
            throw new InvalidInputException("El teléfono debe tener exactamente 8 caracteres y no estar vacío");
        }
        if (input.address() == null || input.address().length() < 3 || input.address().isEmpty()) {
            throw new InvalidInputException("La dirección debe tener al menos 3 caracteres y no estar vacía");
        }
        if (input.email() == null || input.email().isEmpty()) {
            throw new InvalidInputException("El email es inválido o vacío");
        }

        boolean exists = false;

        exists = providerRepository
                .existsByNameAndProvinceIdAndServiceTypeIdAndProviderTypeIdAndEmailAndPhoneAndAddress(
                        input.name(),
                        input.provinceId(),
                        input.serviceTypeId(),
                        input.providerTypeId(),
                        input.email(),
                        input.phone(),
                        input.address());
        if (input.providerTypeId() == veterinarianType) {
            exists = veterinarianRepository.existsByFaxAndClinicIdAndSpecialityId(
                    input.fax(),
                    input.clinicId(),
                    input.specialityId());
        }
        return exists;
    }

    private ProviderResponseDTO mapToOutputDTO(Provider provider) {
        if (provider.getProviderType().getId() == veterinarianType) {
            VeterinarianOutputDTO veterinarianOutputDTO = veterinarianService.findById(provider.getId());

            return new ProviderWithVeterinarianOutputDTO(
                    provider.getId(),
                    provider.getName(),
                    provider.getProvince().getId(),
                    provider.getServiceType().getId(),
                    provider.getProviderType().getId(),
                    provider.getAddress(),
                    provider.getPhone(),
                    provider.getEmail(),
                    provider.getResponsibleName(),
                    provider.getProvince().getName(),
                    provider.getServiceType().getName(),
                    provider.getProviderType().getName(),
                    veterinarianOutputDTO.fax(),
                    veterinarianOutputDTO.cityDistance(),
                    veterinarianOutputDTO.clinicId(),
                    veterinarianOutputDTO.specialityId(),
                    veterinarianOutputDTO.clinicName(),
                    veterinarianOutputDTO.specialityName());
        } else {
            return new ProviderOutputDTO(
                    provider.getId(),
                    provider.getName(),
                    provider.getProvince().getId(),
                    provider.getServiceType().getId(),
                    provider.getProviderType().getId(),
                    provider.getAddress(),
                    provider.getPhone(),
                    provider.getEmail(),
                    provider.getResponsibleName(),
                    provider.getProvince().getName(),
                    provider.getServiceType().getName(),
                    provider.getProviderType().getName());
        }
    }

    public Page<ProviderResponseDTO> searchProvider(ProviderSearchCriteria criteria) {
        // Todo esta validado en animalSpecification

        Specification<Provider> spec = Specification.where(null);

        // Aplicar cada filtro si es válido
        if (criteria.searchField() != null && !criteria.searchField().isEmpty()) {
            spec = spec.and(ProviderSpecification.filterBySearchField(criteria.searchField()));
        }
        if (criteria.provinceId() > 0) {
            spec = spec.and(ProviderSpecification.filterByProvince(criteria.provinceId()));
        }
        if (criteria.serviceTypeId() > 0) {
            spec = spec.and(ProviderSpecification.filterByServiceType(criteria.serviceTypeId()));
        }
        if (criteria.providerTypeId() > 0) {
            spec = spec.and(ProviderSpecification.filterByProviderType(criteria.providerTypeId()));
        }

        // Crear un objeto Pageable usando pageNumber y itemsPerPage
        Pageable pageable = PageRequest.of(criteria.pageNumber(), criteria.itemsPerPage(), Sort.by("id"));

        // Obtener la lista de animales según la especificación y la paginación
        Page<Provider> providerPage = providerRepository.findAll(spec, pageable);

        // Convertir a DTOs
        return providerPage.map(this::mapToOutputDTO);
    }

}
