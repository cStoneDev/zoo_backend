package org.app.zoo.providertype;

import java.util.List;
import java.util.stream.Collectors;

import org.app.zoo.providertype.dto.in.ProviderTypeInputDTO;
import org.app.zoo.providertype.dto.out.ProviderTypeOutputDTO;
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

    public List<ProviderTypeOutputDTO> getAllProviderTypes(){
        return providerTypeRepository.findAll().stream()
        .map(this::mapToOutputDTO)
        .collect(Collectors.toList());
    }

    private ProviderTypeOutputDTO mapToOutputDTO(ProviderType providerType){
        return new ProviderTypeOutputDTO(providerType.getName());
    }
}
